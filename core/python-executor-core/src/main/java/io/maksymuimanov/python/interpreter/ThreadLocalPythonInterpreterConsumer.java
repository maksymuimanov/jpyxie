package io.maksymuimanov.python.interpreter;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@RequiredArgsConstructor
public class ThreadLocalPythonInterpreterConsumer<I extends AutoCloseable> implements PythonInterpreterConsumer<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final ThreadLocal<@Nullable I> threadLocal;

    public ThreadLocalPythonInterpreterConsumer(PythonInterpreterFactory<I> interpreterFactory) {
        this(interpreterFactory, new ThreadLocal<>());
    }

    @Override
    public I consume() {
        I threadLocalValue = threadLocal.get();
        if (threadLocalValue == null) {
            I instance = this.interpreterFactory.create();
            threadLocal.set(instance);
            return instance;
        }
        return threadLocalValue;
    }

    @Override
    public void close() throws Exception {
        Objects.requireNonNull(this.threadLocal.get()).close();
        this.threadLocal.remove();
    }
}
