package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicBoolean;

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
                    this.interpreter = interpreterFactory.create();
                }
            }
        }

        if (this.closed.get()) throw new PythonInterpreterProvisionException("Interpreter is closed");
        return this.interpreter;
    }

    @Override
    public void close() throws Exception {
        if (!this.closed.compareAndSet(false, true)) return;
        try {
            this.interpreter.close();
        } catch (Exception e) {
            throw new PythonInterpreterProvisionException(e);
        }
    }
}
