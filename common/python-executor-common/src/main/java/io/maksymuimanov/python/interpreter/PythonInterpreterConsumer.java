package io.maksymuimanov.python.interpreter;

public interface PythonInterpreterConsumer<I extends AutoCloseable> extends AutoCloseable {
    I consume();
}
