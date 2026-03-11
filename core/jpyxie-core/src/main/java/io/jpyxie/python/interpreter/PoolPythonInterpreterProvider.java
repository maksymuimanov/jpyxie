package io.jpyxie.python.interpreter;

import io.jpyxie.python.exception.PythonInterpreterProvisionException;
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
    public static final int DEFAULT_POOL_SIZE = 16;
    public static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(1);
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final BlockingQueue<I> pool;
    private final AtomicInteger poolSize;
    private final Duration timeout;
    private final AtomicBoolean filled;
    private final AtomicBoolean closed;

    public PoolPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this(interpreterFactory, DEFAULT_POOL_SIZE, DEFAULT_TIMEOUT);
    }

    public PoolPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, int poolSize, Duration timeout) {
        this(interpreterFactory, new ArrayBlockingQueue<>(poolSize), poolSize, timeout);
    }

    public PoolPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, BlockingQueue<I> pool, int poolSize, Duration timeout) {
        this(interpreterFactory, pool, new AtomicInteger(poolSize), timeout);
    }

    public PoolPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, BlockingQueue<I> pool, AtomicInteger poolSize, Duration timeout) {
        this.interpreterFactory = interpreterFactory;
        this.pool = pool;
        this.poolSize = poolSize;
        this.timeout = timeout;
        this.filled = new AtomicBoolean(false);
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public I acquire() {
        return this.acquire(this.timeout.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public I acquire(long timeout, TimeUnit unit) {
        log.debug("Acquiring interpreter from pool [available: {}, pool size: {}]", this.pool.size(), this.poolSize.get());
        if (this.closed.get()) {
            log.warn("Attempted to acquire interpreter from closed pool");
            throw new PythonInterpreterProvisionException("Pool is closed");
        }
        try {
            if (this.pool.isEmpty()
                    && this.filled.compareAndSet(false, true)) {
                this.fillPool(interpreterFactory);
            }
            log.debug("Attempting to acquire interpreter from pool");
            I polled = this.pool.poll(timeout, unit);
            if (polled == null) {
                PythonInterpreterProvisionException exception = new PythonInterpreterProvisionException("Pool starvation detected");
                log.error(exception.getMessage(), exception);
                throw exception;
            }
            log.debug("Interpreter acquired from pool successfully");
            return polled;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interpreter acquisition interrupted", e);
            throw new PythonInterpreterProvisionException(e);
        } catch (Exception e) {
            log.error("Failed to acquire interpreter from pool", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }

    protected void fillPool(PythonInterpreterFactory<I> interpreterFactory) {
        synchronized (this.pool) {
            log.debug("Filling interpreter pool [size: {}]", this.pool.size());
            for (int i = 0; i < this.poolSize.get(); i++) {
                I interpreter = interpreterFactory.create();
                boolean offered = this.pool.offer(interpreter);
                if (!offered) {
                    PythonInterpreterProvisionException exception = new PythonInterpreterProvisionException("Failed to create interpreter during pool expansion");
                    log.error(exception.getMessage(), exception);
                    throw exception;
                }
            }
            log.debug("Pool filled successfully [available: {}]", this.pool.size());
        }
    }

    @Override
    public void release(@Nullable I interpreter) {
        if (interpreter == null) {
            log.debug("Attempted to release null interpreter, ignoring");
            return;
        }
        try {
            if (this.closed.get()) {
                try {
                    log.debug("Pool is closed, closing released interpreter");
                    interpreter.close();
                } catch (Exception e) {
                    log.error("Failed to close interpreter during release", e);
                    throw new PythonInterpreterProvisionException(e);
                }
            } else {
                boolean offered = this.pool.offer(interpreter);
                if (!offered) {
                    PythonInterpreterProvisionException exception = new PythonInterpreterProvisionException("Failed to return interpreter to pool");
                    log.error(exception.getMessage(), exception);
                    throw exception;
                }
                log.debug("Interpreter returned to pool [available: {}]", this.pool.size());
            }
        } catch (Exception e) {
            log.error("Failed to return interpreter to pool", e);
            throw new PythonInterpreterProvisionException(e);
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