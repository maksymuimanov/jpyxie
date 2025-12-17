package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.interpreter.ReleasablePythonInterpreterProvider;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;

public abstract class InterpretablePythonExecutor<I extends AutoCloseable> implements PythonExecutor {
    private final PythonInterpreterProvider<I> interpreterProvider;

    protected InterpretablePythonExecutor(PythonInterpreterProvider<I> interpreterProvider) {
        this.interpreterProvider = interpreterProvider;
    }

    public <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass) {
        try {
            I interpreter = interpreterProvider.acquire();
            PythonExecutionResponse<R> response = this.execute(script, resultClass, interpreter);
            if (interpreterProvider instanceof ReleasablePythonInterpreterProvider<I> releasableProvider) {
                releasableProvider.release(interpreter);
            }
            return response;
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    protected abstract <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass, I interpreter) throws Exception;
}

