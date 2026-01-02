package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.interpreter.ReleasablePythonInterpreterProvider;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;

public abstract class InterpretablePythonExecutor<I extends AutoCloseable> implements PythonExecutor {
    private final PythonInterpreterProvider<I> interpreterProvider;

    protected InterpretablePythonExecutor(PythonInterpreterProvider<I> interpreterProvider) {
        this.interpreterProvider = interpreterProvider;
    }

    @Override
    public PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec) {
        try {
            I interpreter = interpreterProvider.acquire();
            PythonResultMap resultMap = this.execute(script, resultSpec, interpreter);
            if (interpreterProvider instanceof ReleasablePythonInterpreterProvider<I> releasableProvider) {
                releasableProvider.release(interpreter);
            }
            return resultMap;
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    protected abstract PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec, I interpreter) throws Exception;

    protected PythonInterpreterProvider<I> getInterpreterProvider() {
        return interpreterProvider;
    }
}

