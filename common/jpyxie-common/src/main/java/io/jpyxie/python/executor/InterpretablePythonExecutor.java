package io.jpyxie.python.executor;

import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.interpreter.PythonReleasableInterpreterProvider;
import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.script.PythonScript;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PROTECTED)
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
            return this.executeAndRelease(script, resultSpec, interpreter);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    private PythonResultMap executeAndRelease(PythonScript script, PythonResultSpec resultSpec, I interpreter) throws Exception {
        PythonResultMap resultMap;
        try {
            resultMap = this.execute(script, resultSpec, interpreter);
        } finally {
            if (interpreterProvider instanceof PythonReleasableInterpreterProvider<I> releasableProvider) {
                releasableProvider.release(interpreter);
            }
        }
        return resultMap;
    }

    protected abstract PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec, I interpreter) throws Exception;
}

