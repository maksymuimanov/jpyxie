package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Getter(AccessLevel.PROTECTED)
public class SingletonPythonInterpreterProvider<I extends AutoCloseable> implements PythonInterpreterProvider<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final AtomicBoolean closed;
    private volatile I interpreter;

    public SingletonPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this.interpreterFactory = interpreterFactory;
        this.closed = new AtomicBoolean(false);
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public I acquire() {
        if (this.interpreter == null) {
            synchronized (SingletonPythonInterpreterProvider.class) {
                if (interpreter == null) {
                    log.debug("Creating new singleton interpreter instance");
                    this.interpreter = interpreterFactory.create();
                    log.info("Singleton interpreter initialized successfully");
                }
            }
        }

        if (this.closed.get()) {
            log.warn("Attempted to acquire closed singleton interpreter");
            throw new PythonInterpreterProvisionException("Interpreter is closed");
        }
        return this.interpreter;
    }

    @Override
    public void close() throws Exception {
        if (!this.closed.compareAndSet(false, true)) {
            log.debug("Singleton interpreter is already closed");
            return;
        }
        try {
            log.info("Closing singleton interpreter instance");
            this.interpreter.close();
        } catch (Exception e) {
            log.error("Failed to close singleton interpreter", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }
}
