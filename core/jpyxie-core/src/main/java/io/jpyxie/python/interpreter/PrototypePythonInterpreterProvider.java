package io.jpyxie.python.interpreter;

import io.jpyxie.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Getter(AccessLevel.PROTECTED)
public class PrototypePythonInterpreterProvider<I extends AutoCloseable> implements PythonReleasableInterpreterProvider<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final AtomicBoolean closed;

    public PrototypePythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this.interpreterFactory = interpreterFactory;
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public I acquire() {
        if (this.closed.get()) {
            log.warn("Attempted to acquire interpreter from closed prototype interpreter provider");
            throw new PythonInterpreterProvisionException("Prototype interpreter provider is closed");
        }
        try {
            return this.interpreterFactory.create();
        } catch (Exception e) {
            log.error("Failed to acquire interpreter from prototype interpreter provider", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }

    @Override
    public void release(@Nullable I interpreter) {
        if (interpreter == null) {
            log.debug("Attempted to release null interpreter, ignoring");
            return;
        }
        try {
            interpreter.close();
            log.debug("Interpreter closed");
        } catch (Exception e) {
            log.error("Failed to release interpreter to prototype interpreter provider", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (!this.closed.compareAndSet(false, true)) {
                log.debug("Prototype interpreter provider is already closed");
                return;
            }
            log.info("Successfully closed prototype interpreter provider");
        } catch (Exception e) {
            log.error("Failed to close prototype interpreter provider", e);
            throw new PythonInterpreterProvisionException(e);
        }
    }
}