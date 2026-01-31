package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter(AccessLevel.PROTECTED)
public class PoolPythonInterpreterProvider<I extends AutoCloseable> implements PythonReleasableInterpreterProvider<I> {
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
    }

    @Override
    public I acquire() {
        log.debug("Acquiring interpreter from pool [available: {}, pool size: {}]", this.pool.size(), this.poolSize.get());
        if (this.pool.isEmpty()) this.fillPool(interpreterFactory);
        return this.acquire(this.timeout.toMillis(), TimeUnit.MILLISECONDS);
    }

    protected void fillPool(PythonInterpreterFactory<I> interpreterFactory) {
        log.debug("Filling interpreter pool [size: {}]", this.poolSize.get());
        for (int i = 0; i < this.poolSize.get(); i++) {
            this.pool.offer(interpreterFactory.create());
        }
        log.debug("Pool filled successfully [available: {}]", this.pool.size());
    }

    @Override
    public I acquire(long timeout, TimeUnit unit) {
        if (this.closed.get()) {
            log.warn("Attempted to acquire interpreter from closed pool");
            throw new PythonInterpreterProvisionException("Pool is closed");
        }
        try {
            I polled = this.pool.poll(timeout, unit);
            if (polled == null) {
                log.debug("Pool starvation detected, invoking handler");
                return this.poolStarvationHandler.handle(this.interpreterFactory, this.pool, this.poolSize);
            }
            log.debug("Interpreter acquired from pool successfully");
            return polled;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interpreter acquisition interrupted", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }

    @Override
    public void release(@Nullable I interpreter) {
        if (interpreter == null) {
            log.debug("Attempted to release null interpreter, ignoring");
            return;
        }
        if (this.closed.get()) {
            try {
                log.debug("Pool is closed, closing released interpreter");
                interpreter.close();
            } catch (Exception e) {
                log.error("Failed to close interpreter during release", e);
                throw new PythonInterpreterProvisionException(e);
            }
        } else {
            this.pool.offer(interpreter);
            log.debug("Interpreter returned to pool [available: {}]", this.pool.size());
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (!this.closed.compareAndSet(false, true)) {
                log.debug("Pool is already closed");
                return;
            }
            int size = this.pool.size();
            log.info("Closing interpreter pool [available: {}]", size);
            I current;
            while ((current = this.pool.poll()) != null) {
                current.close();
            }
            log.info("Successfully closed [{}] interpreters", size);
        } catch (Exception e) {
            log.error("Failed to close interpreter pool", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }
}