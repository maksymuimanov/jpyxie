package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvidenceException;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicBoolean;

@Getter(AccessLevel.PROTECTED)
public class SingletonPythonInterpreterProvider<I extends AutoCloseable> implements PythonInterpreterProvider<I> {
    private final I interpreter;
    private final AtomicBoolean closed;

    public SingletonPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this.interpreter = interpreterFactory.create();
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public I acquire() {
        if (this.closed.get()) throw new PythonInterpreterProvidenceException("Interpreter is closed");
        return this.interpreter;
    }

    @Override
    public void close() throws Exception {
        if (!this.closed.compareAndSet(false, true)) return;
        try {
            this.interpreter.close();
        } catch (Exception e) {
            throw new PythonInterpreterProvidenceException(e);
        }
    }
}
