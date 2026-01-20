package io.maksymuimanov.python.interpreter;

public interface PythonInterpreterProvider<I extends AutoCloseable> extends AutoCloseable {
    I acquire();
}
