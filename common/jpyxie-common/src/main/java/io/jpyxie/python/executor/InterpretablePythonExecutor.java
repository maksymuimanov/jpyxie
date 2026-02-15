package io.jpyxie.python.executor;

import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.exception.PythonExecutionException;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.interpreter.PythonReleasableInterpreterProvider;
import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.script.PythonScript;

public abstract class InterpretablePythonExecutor<F, I extends AutoCloseable> extends AbstractPythonExecutor<F> {
    private final PythonInterpreterProvider<I> interpreterProvider;

    protected InterpretablePythonExecutor(PythonDeserializer<F> pythonDeserializer,
                                          PythonInterpreterProvider<I> interpreterProvider) {
        super(pythonDeserializer);
        this.interpreterProvider = interpreterProvider;
    }

    @Override
    public PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec) {
        try {
            I interpreter = interpreterProvider.acquire();
            PythonResultMap resultMap = this.execute(script, resultSpec, interpreter);
            if (interpreterProvider instanceof PythonReleasableInterpreterProvider<I> releasableProvider) {
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

