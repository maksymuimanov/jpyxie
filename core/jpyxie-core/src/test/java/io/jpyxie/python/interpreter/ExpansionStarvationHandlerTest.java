package io.jpyxie.python.interpreter;

import io.jpyxie.python.exception.PythonInterpreterProvisionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpansionStarvationHandlerTest {
    private ExpansionStarvationHandler<AutoCloseable> handler;
    @Mock
    private PythonInterpreterFactory<AutoCloseable> interpreterFactory;
    private BlockingQueue<AutoCloseable> pool;
    private AtomicInteger poolSize;
    @Mock
    private AutoCloseable newInterpreter;

    @BeforeEach
    void setUp() {
        handler = new ExpansionStarvationHandler<>();
        poolSize = spy(new AtomicInteger(5));
        pool = spy(new ArrayBlockingQueue<>(poolSize.get()));
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);
    }

    @Test
    @SuppressWarnings("resource")
    void handle_shouldCreateNewInterpreterAndIncreasePoolSize() {
        AutoCloseable result = handler.handle(interpreterFactory, pool, poolSize);

        assertThat(result)
                .isEqualTo(newInterpreter);
        assertThat(poolSize.get())
                .isEqualTo(10);
        assertDoesNotThrow(() -> pool.offer(newInterpreter));
        verify(interpreterFactory, times(5))
                .create();
        verify(pool, times(6))
                .offer(newInterpreter);
    }

    @Test
    @SuppressWarnings("resource")
    void handle_shouldFail_whenPoolOfferFails() {
        when(pool.offer(newInterpreter))
                .thenReturn(false);

        assertThatThrownBy(() -> handler.handle(interpreterFactory, pool, poolSize))
                .isInstanceOf(PythonInterpreterProvisionException.class);
        assertThat(poolSize.get())
                .isEqualTo(5);
        verify(pool)
                .offer(newInterpreter);
    }

    @Test
    @SuppressWarnings("resource")
    void handle_shouldFail_whenInterrupted() {
        doAnswer(invocation -> { throw new InterruptedException(); })
                .when(pool)
                .offer(newInterpreter);

        assertThatThrownBy(() -> handler.handle(interpreterFactory, pool, poolSize))
                .isInstanceOf(PythonInterpreterProvisionException.class)
                .hasCauseInstanceOf(InterruptedException.class);
        assertThat(Thread.interrupted())
                .isTrue();
        assertThat(poolSize.get())
                .isEqualTo(5);
        verify(pool, times(1))
                .offer(newInterpreter);
    }
}
