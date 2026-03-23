package io.jpyxie.python.interpreter;

import org.jspecify.annotations.Nullable;

public interface PythonReleasableInterpreterProvider<I extends AutoCloseable> extends PythonInterpreterProvider<I> {
    void release(@Nullable I interpreter);
}
