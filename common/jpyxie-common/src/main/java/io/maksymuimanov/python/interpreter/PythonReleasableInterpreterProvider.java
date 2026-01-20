package io.maksymuimanov.python.interpreter;

import org.jspecify.annotations.Nullable;

import java.util.concurrent.TimeUnit;

public interface PythonReleasableInterpreterProvider<I extends AutoCloseable> extends PythonInterpreterProvider<I> {
    I acquire(long timeout, TimeUnit unit);

    void release(@Nullable I interpreter);
}
