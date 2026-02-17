package io.jpyxie.python.interpreter;

import io.jpyxie.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Getter(AccessLevel.PROTECTED)
public class SingletonPythonInterpreterProvider<I extends AutoCloseable> implements PythonInterpreterProvider<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final AtomicBoolean closed;
    @Nullable
    private volatile I interpreter;

    public SingletonPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this.interpreterFactory = interpreterFactory;
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public I acquire() {
        try {
            if (this.closed.get()) {
                PythonInterpreterProvisionException exception = new PythonInterpreterProvisionException("Failed to acquire interpreter, interpreter is closed");
                log.error(exception.getMessage(), exception);
                throw exception;
            }

            if (this.interpreter == null) {
                synchronized (SingletonPythonInterpreterProvider.class) {
                    if (interpreter == null) {
                        log.debug("Creating new singleton interpreter instance");
                        this.interpreter = this.interpreterFactory.create();
                        log.info("Singleton interpreter initialized successfully");
                    }
                }
            }

            return this.interpreter;
        } catch (Exception e) {
            PythonInterpreterProvisionException exception = new PythonInterpreterProvisionException("Failed to acquire singleton interpreter", e);
            log.error(exception.getMessage(), e);
            throw exception;
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (!this.closed.compareAndSet(false, true)) {
                log.debug("Singleton interpreter is already closed");
                return;
            }

            if (this.interpreter != null) {
                log.info("Closing singleton interpreter instance");
                this.interpreter.close();
            }
        } catch (Exception e) {
            log.error("Failed to close singleton interpreter", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }
}
