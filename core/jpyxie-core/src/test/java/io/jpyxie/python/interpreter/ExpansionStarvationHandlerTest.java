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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpansionStarvationHandlerTest {
    private ExpansionStarvationHandler<AutoCloseable> handler;
    @Mock
    private PythonInterpreterFactory<AutoCloseable> interpreterFactory;
    private BlockingQueue<AutoCloseable> pool;
    @Mock
    private AutoCloseable newInterpreter;
    private AtomicInteger poolSize;

    @BeforeEach
    void setUp() {
        handler = new ExpansionStarvationHandler<>();
        poolSize = new AtomicInteger(5);
        pool = spy(new ArrayBlockingQueue<>(poolSize.get()));
        when(interpreterFactory.create()).thenReturn(newInterpreter);
    }

    @Test
    @SuppressWarnings("resource")
    void handleShouldCreateNewInterpreterAndIncreasePoolSize() {
        AutoCloseable result = handler.handle(interpreterFactory, pool, poolSize);

        assertEquals(newInterpreter, result);
        assertEquals(10, poolSize.get());
        assertDoesNotThrow(() -> pool.offer(newInterpreter));
        verify(interpreterFactory, times(5)).create();
        verify(pool, times(6)).offer(newInterpreter);
    }

    @Test
    @SuppressWarnings("resource")
    void handleShouldFailWhenPoolOfferFails() {
        when(pool.offer(newInterpreter)).thenReturn(false);

        assertThrows(PythonInterpreterProvisionException.class, () -> handler.handle(interpreterFactory, pool, poolSize));
        assertEquals(10, poolSize.get());
        verify(pool).offer(newInterpreter);
    }
}
