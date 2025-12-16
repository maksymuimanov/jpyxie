package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.interpreter.PythonInterpreterFactory;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;

public abstract class InterpretablePythonExecutor<I extends AutoCloseable> implements PythonExecutor {
    private final PythonInterpreterFactory<I> interpreterFactory;

    protected InterpretablePythonExecutor(PythonInterpreterFactory<I> interpreterFactory) {
        this.interpreterFactory = interpreterFactory;
    }

    public <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass) {
        try (I interpreter = interpreterFactory.create()) {
            return this.execute(script, resultClass, interpreter);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    protected abstract <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass, I interpreter) throws Exception;
}

