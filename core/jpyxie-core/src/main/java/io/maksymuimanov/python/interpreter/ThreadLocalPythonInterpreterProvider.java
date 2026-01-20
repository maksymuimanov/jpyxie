package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.exception.PythonInterpreterProvisionException;
import lombok.AccessLevel;
import lombok.Getter;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter(AccessLevel.PROTECTED)
public class ThreadLocalPythonInterpreterProvider<I extends AutoCloseable> implements PythonInterpreterProvider<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final ThreadLocal<@Nullable I> threadLocal;
    private final AtomicBoolean closed;

    public ThreadLocalPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory) {
        this(interpreterFactory, new ThreadLocal<>());
    }

    public ThreadLocalPythonInterpreterProvider(PythonInterpreterFactory<I> interpreterFactory, ThreadLocal<@Nullable I> threadLocal) {
        this.interpreterFactory = interpreterFactory;
        this.threadLocal = threadLocal;
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public I acquire() {
        if (this.closed.get()) throw new PythonInterpreterProvisionException("Interpreter is closed");
        try {
            I threadLocalInterpreter = threadLocal.get();
            if (threadLocalInterpreter == null) {
                I interpreter = this.interpreterFactory.create();
                threadLocal.set(interpreter);
                return interpreter;
            }
            return threadLocalInterpreter;
        } catch (Exception e) {
            throw new PythonInterpreterProvisionException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (!this.closed.compareAndSet(false, true)) return;
        try {
            Objects.requireNonNull(this.threadLocal.get()).close();
            this.threadLocal.remove();
        } catch (Exception e) {
            throw new PythonInterpreterProvisionException(e);
        }
    }
}
