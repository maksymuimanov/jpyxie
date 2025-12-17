package io.maksymuimanov.python.interpreter;

public class SingletonPythonInterpreterConsumer<I extends AutoCloseable> implements PythonInterpreterConsumer<I> {
    private final I interpreter;

    public SingletonPythonInterpreterConsumer(PythonInterpreterFactory<I> interpreterFactory) {
        this.interpreter = interpreterFactory.create();
    }

    @Override
    public I consume() {
        return this.interpreter;
    }

    @Override
    public void close() throws Exception {
        this.interpreter.close();
    }
}
