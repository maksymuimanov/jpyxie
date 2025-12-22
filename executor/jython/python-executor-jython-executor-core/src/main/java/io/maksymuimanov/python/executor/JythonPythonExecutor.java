package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;
import org.python.util.PythonInterpreter;

import java.util.Map;

public class JythonPythonExecutor extends InterpretablePythonExecutor<PythonInterpreter, PythonInterpreter> {
    public JythonPythonExecutor(PythonResultFieldNameProvider resultFieldProvider, PythonInterpreterProvider<PythonInterpreter> interpreterProvider) {
        super(resultFieldProvider, interpreterProvider);
    }

    @Override
    protected @Nullable <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription, PythonInterpreter interpreter) throws Exception {
        interpreter.exec(script.toPythonString());
        return this.getResult(resultDescription, interpreter);
    }

    @Override
    protected Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, PythonInterpreter interpreter) throws Exception {
        interpreter.exec(script.toPythonString());
        return this.getResultMap(resultDescriptions, interpreter);
    }

    @Override
    protected @Nullable <R> R getResult(PythonResultDescription<R> resultDescription, PythonInterpreter resultContainer) {
        return resultDescription.getValue((type, fieldName) -> resultContainer.get(fieldName, type));
    }
}