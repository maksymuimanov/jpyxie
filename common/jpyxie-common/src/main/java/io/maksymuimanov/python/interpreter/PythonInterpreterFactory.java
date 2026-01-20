package io.maksymuimanov.python.interpreter;

public interface PythonInterpreterFactory<I extends AutoCloseable> {
    I create();
}
