package io.jpyxie.python.interpreter;

import io.jpyxie.python.exception.PythonInterpreterProvisionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class FailFastStarvationHandlerTest {
    private FailFastStarvationHandler<AutoCloseable> handler;
    @Mock
    private PythonInterpreterFactory<AutoCloseable> interpreterFactory;
    @Mock
    private BlockingQueue<AutoCloseable> pool;
    private AtomicInteger poolSize;

    @BeforeEach
    void setUp() {
        handler = new FailFastStarvationHandler<>();
        poolSize = new AtomicInteger(5);
    }

    @Test
    @SuppressWarnings("resource")
    void handle_shouldThrowPythonInterpreterProvisionException() {
        assertThatThrownBy(() -> handler.handle(interpreterFactory, pool, poolSize))
                .isInstanceOf(PythonInterpreterProvisionException.class);
        verifyNoInteractions(interpreterFactory);
        verifyNoInteractions(pool);
    }
}
