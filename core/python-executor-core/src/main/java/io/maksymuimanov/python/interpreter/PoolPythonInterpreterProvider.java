package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Getter(AccessLevel.PROTECTED)
public class PoolPythonInterpreterProvider<I extends AutoCloseable> implements ReleasablePythonInterpreterProvider<I> {
    public static final int DEFAULT_POOL_SIZE = 8;
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final BlockingQueue<I> pool;
    private final AtomicInteger poolSize;
    private final Duration timeout;
    private final PythonInterpreterPoolStarvationHandler<I> poolStarvationHandler;
    private final AtomicBoolean closed;

    public PoolPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, PythonInterpreterPoolStarvationHandler<I> poolStarvationHandler) {
        this(interpreterFactory, DEFAULT_POOL_SIZE, DEFAULT_TIMEOUT, poolStarvationHandler);
    }

    public PoolPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, int poolSize, Duration timeout, PythonInterpreterPoolStarvationHandler<I> poolStarvationHandler) {
        this(interpreterFactory, new ArrayBlockingQueue<>(poolSize), poolSize, timeout, poolStarvationHandler);
    }

    public PoolPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, BlockingQueue<I> pool, int poolSize, Duration timeout, PythonInterpreterPoolStarvationHandler<I> poolStarvationHandler) {
        this.interpreterFactory = interpreterFactory;
        this.pool = pool;
        this.poolSize = new AtomicInteger(poolSize);
        this.timeout = timeout;
        this.poolStarvationHandler = poolStarvationHandler;
        this.closed = new AtomicBoolean(false);
        this.fillPool(interpreterFactory);
    }

    protected void fillPool(PythonInterpreterFactory<I> interpreterFactory) {
        for (int i = 0; i < this.poolSize.get(); i++) {
            this.pool.offer(interpreterFactory.create());
        }
    }

    @Override
    public I acquire() {
        return this.acquire(this.timeout.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public I acquire(long timeout, TimeUnit unit) {
        if (this.closed.get()) throw new PythonInterpreterProvisionException("Pool is closed");
        try {
            I polled = this.pool.poll(timeout, unit);
            if (polled == null) return this.poolStarvationHandler.handle(this.interpreterFactory, this.pool, this.poolSize);
            return polled;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PythonInterpreterProvisionException(e);
        }
    }

    @Override
    public void release(@Nullable I interpreter) {
        if (interpreter == null) return;
        if (this.closed.get()) {
            try {
                interpreter.close();
            } catch (Exception e) {
                throw new PythonInterpreterProvisionException(e);
            }
        } else {
            this.pool.offer(interpreter);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (!this.closed.compareAndSet(false, true)) return;
            I current;
            while ((current = this.pool.poll()) != null) {
                current.close();
            }
        } catch (Exception e) {
            throw new PythonInterpreterProvisionException(e);
        }
    }
}