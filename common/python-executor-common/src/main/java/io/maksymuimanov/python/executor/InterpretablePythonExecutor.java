package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.interpreter.ReleasablePythonInterpreterProvider;
import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public abstract class InterpretablePythonExecutor<C, I extends AutoCloseable> extends AbstractPythonExecutor<C> {
    private final PythonInterpreterProvider<I> interpreterProvider;

    protected InterpretablePythonExecutor(PythonResultFieldNameProvider resultFieldProvider, PythonInterpreterProvider<I> interpreterProvider) {
        super(resultFieldProvider);
        this.interpreterProvider = interpreterProvider;
    }

    @Override
    @Nullable
    public <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription) {
        try {
            I interpreter = interpreterProvider.acquire();
            R response = this.execute(script, resultDescription, interpreter);
            if (interpreterProvider instanceof ReleasablePythonInterpreterProvider<I> releasableProvider) {
                releasableProvider.release(interpreter);
            }
            return response;
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    @Override
    public Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        try {
            I interpreter = interpreterProvider.acquire();
            Map<String, @Nullable Object> response = this.execute(script, resultDescriptions, interpreter);
            if (interpreterProvider instanceof ReleasablePythonInterpreterProvider<I> releasableProvider) {
                releasableProvider.release(interpreter);
            }
            return response;
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    @Nullable
    protected abstract <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription, I interpreter) throws Exception;

    protected abstract Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, I interpreter) throws Exception;

    protected PythonInterpreterProvider<I> getInterpreterProvider() {
        return interpreterProvider;
    }
}

