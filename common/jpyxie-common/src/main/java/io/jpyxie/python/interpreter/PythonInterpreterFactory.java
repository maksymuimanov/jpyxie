package io.jpyxie.python.interpreter;

public interface PythonInterpreterFactory<I extends AutoCloseable> {
    I create();
}
