package io.jpyxie.python.interpreter;

import io.jpyxie.python.exception.PythonInterpreterProvisionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoolPythonInterpreterProviderTest {
    private PoolPythonInterpreterProvider<AutoCloseable> interpreterProvider;
    @Mock
    private PythonInterpreterFactory<AutoCloseable> interpreterFactory;
    private BlockingQueue<AutoCloseable> pool;
    private AtomicInteger poolSize;
    private Duration timeout;
    @Mock
    private PythonInterpreterPoolStarvationHandler<AutoCloseable> poolStarvationHandler;
    @Mock
    private AutoCloseable newInterpreter;

    @BeforeEach
    void setUp() {
        poolSize = new AtomicInteger(8);
        pool = spy(new ArrayBlockingQueue<>(poolSize.get()));
        timeout = spy(Duration.ofSeconds(2));
        interpreterProvider = new PoolPythonInterpreterProvider<>(interpreterFactory, pool, poolSize, timeout, poolStarvationHandler);
    }

    @Test
    @SuppressWarnings("resource")
    void acquire_shouldReturnInterpreterFromPool() {
        this.pool.add(newInterpreter);

        AutoCloseable interpreter = interpreterProvider.acquire();

        assertThat(interpreter)
                .isNotNull()
                .isEqualTo(newInterpreter);
        assertThat(pool)
                .isEmpty();
        verify(poolStarvationHandler, never())
                .handle(interpreterFactory, pool, poolSize);
        verify(timeout, times(1))
                .toMillis();
    }

    @Test
    @SuppressWarnings("resource")
    void acquireWithTimeout_shouldReturnInterpreterFromPool() {
        this.pool.add(newInterpreter);

        AutoCloseable interpreter = interpreterProvider.acquire(5, TimeUnit.SECONDS);

        assertThat(interpreter)
                .isNotNull()
                .isEqualTo(newInterpreter);
        assertThat(pool)
                .isEmpty();
        verify(poolStarvationHandler, never())
                .handle(interpreterFactory, pool, poolSize);
    }

    @Test
    @SuppressWarnings("resource")
    void acquireWithTimeout_shouldReturnInterpreterFromPool_whenNotInitialized() {
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);

        AutoCloseable interpreter = interpreterProvider.acquire(5, TimeUnit.SECONDS);

        assertThat(interpreter)
                .isNotNull()
                .isEqualTo(newInterpreter);
        assertThat(pool)
                .hasSize(poolSize.get() - 1);
        verify(poolStarvationHandler, never())
                .handle(interpreterFactory, pool, poolSize);
    }

    @Test
    @SuppressWarnings("resource")
    void acquireWithTimeout_shouldHandleWithStarvationHandler_whenEmpty() {
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);

        for (int i = 0; i < poolSize.get(); i++) {
            interpreterProvider.acquire(5, TimeUnit.SECONDS);
        }

        assumeThat(pool.isEmpty())
                .isTrue();

        when(poolStarvationHandler.handle(interpreterFactory, pool, poolSize))
                .thenReturn(newInterpreter);

        AutoCloseable interpreter = interpreterProvider.acquire(5, TimeUnit.SECONDS);

        assertThat(interpreter)
                .isNotNull()
                .isEqualTo(newInterpreter);
        assertThat(pool)
                .isEmpty();
        verify(poolStarvationHandler, times(1))
                .handle(interpreterFactory, pool, poolSize);
    }

    @Test
    @SuppressWarnings("resource")
    void acquireWithTimeout_shouldFail_whenClosed() throws Exception {
        interpreterProvider.close();

        assertThatThrownBy(() -> interpreterProvider.acquire(5, TimeUnit.SECONDS))
                .isInstanceOf(PythonInterpreterProvisionException.class);
        verify(interpreterFactory, never())
                .create();
        verify(poolStarvationHandler, never())
                .handle(interpreterFactory, pool, poolSize);
    }

    @Test
    @SuppressWarnings("resource")
    void acquireWithTimeout_shouldFail_whenInterrupted() {
        doAnswer(invocation -> { throw new InterruptedException(); })
                .when(pool)
                .isEmpty();

        assertThatThrownBy(() -> interpreterProvider.acquire(5, TimeUnit.SECONDS))
                .isInstanceOf(PythonInterpreterProvisionException.class)
                .hasCauseInstanceOf(InterruptedException.class);
        assertThat(Thread.interrupted())
                .isTrue();
        verify(interpreterFactory, never())
                .create();
        verify(poolStarvationHandler, never())
                .handle(interpreterFactory, pool, poolSize);
    }

    @Test
    @SuppressWarnings("resource")
    void acquireWithTimeout_shouldFail_whenExceptionThrown() {
        when(pool.isEmpty())
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> interpreterProvider.acquire(5, TimeUnit.SECONDS))
                .isInstanceOf(PythonInterpreterProvisionException.class);
        verify(interpreterFactory, never())
                .create();
        verify(poolStarvationHandler, never())
                .handle(interpreterFactory, pool, poolSize);
    }

    @Test
    @SuppressWarnings("resource")
    void acquireWithTimeout_shouldFail_whenFillingFailed() {
        when(interpreterFactory.create())
                .thenReturn(newInterpreter);
        when(pool.offer(newInterpreter))
                .thenReturn(false);
        pool.clear();

        assertThatThrownBy(() -> interpreterProvider.acquire(5, TimeUnit.SECONDS))
                .isInstanceOf(PythonInterpreterProvisionException.class);
        verify(poolStarvationHandler, never())
                .handle(interpreterFactory, pool, poolSize);
    }

    @Test
    void release_shouldReturnInterpreterToPool() {
        interpreterProvider.release(newInterpreter);

        assertThat(pool)
                .hasSize(1)
                .containsExactly(newInterpreter);
        verify(pool, times(1))
                .offer(newInterpreter);
    }

    @Test
    void release_shouldIgnoreNull() {
        interpreterProvider.release(null);

        verifyNoInteractions(pool);
    }

    @Test
    void release_shouldClose_whenClosed() throws Exception {
        doNothing()
                .when(newInterpreter)
                .close();

        interpreterProvider.close();
        interpreterProvider.release(newInterpreter);

        assertThat(pool)
                .isEmpty();
        verify(newInterpreter, times(1))
                .close();
        verify(pool, never())
                .offer(newInterpreter);
    }

    @Test
    void release_shouldFail_whenClosedAndCloseFail() throws Exception {
        doThrow(RuntimeException.class)
                .when(newInterpreter)
                .close();

        interpreterProvider.close();

        assertThatThrownBy(() -> interpreterProvider.release(newInterpreter))
                .isInstanceOf(PythonInterpreterProvisionException.class);
        assertThat(pool)
                .isEmpty();
        verify(pool, never())
                .offer(newInterpreter);
    }

    @Test
    void release_shouldFail_whenNotOfferedToPool() {
        when(pool.offer(newInterpreter))
                .thenReturn(false);

        assertThatThrownBy(() -> interpreterProvider.release(newInterpreter))
                .isInstanceOf(PythonInterpreterProvisionException.class);
        verify(pool, times(1))
                .offer(newInterpreter);
    }

    @Test
    void release_shouldFail_whenExceptionThrown() {
        doThrow(RuntimeException.class)
                .when(pool)
                .offer(newInterpreter);

        assertThatThrownBy(() -> interpreterProvider.release(newInterpreter))
                .isInstanceOf(PythonInterpreterProvisionException.class);
        verify(pool, times(1))
                .offer(newInterpreter);
    }

    @Test
    void close_shouldClosePool() throws Exception {
        pool.add(newInterpreter);
        interpreterProvider.close();

        verify(newInterpreter, times(1))
                .close();
    }

    @Test
    void close_shouldIgnore_whenClosed() throws Exception {
        pool.add(newInterpreter);
        interpreterProvider.close();
        interpreterProvider.close();

        verify(newInterpreter, times(1))
                .close();
    }

    @Test
    void close_shouldFail_whenExceptionThrown() throws Exception {
        pool.add(newInterpreter);

        doThrow(RuntimeException.class)
                .when(newInterpreter)
                .close();

        assertThatThrownBy(() -> interpreterProvider.close())
                .isInstanceOf(PythonInterpreterProvisionException.class);
        verify(newInterpreter, times(1))
                .close();
    }
}
