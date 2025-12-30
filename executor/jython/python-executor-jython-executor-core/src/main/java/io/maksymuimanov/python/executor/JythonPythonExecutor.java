package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;
import org.python.util.PythonInterpreter;

import java.util.Map;

public class JythonPythonExecutor extends InterpretablePythonExecutor<PythonInterpreter, PythonInterpreter> {
    public JythonPythonExecutor(PythonInterpreterProvider<PythonInterpreter> interpreterProvider) {
        super(interpreterProvider);
    }

    @Override
    protected @Nullable <R> R execute(PythonScript script, PythonResultSpec<R> resultDescription, PythonInterpreter interpreter) throws Exception {
        interpreter.exec(script.toPythonString());
        return this.getResult(resultDescription, interpreter);
    }

    @Override
    protected Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultSpec<?>> resultDescriptions, PythonInterpreter interpreter) throws Exception {
        interpreter.exec(script.toPythonString());
        return this.getResultMap(resultDescriptions, interpreter);
    }

    @Override
    protected @Nullable <R> R getResult(PythonResultSpec<R> resultDescription, PythonInterpreter resultContainer) {
        return resultDescription.getValue((type, fieldName) -> resultContainer.get(fieldName, type));
    }
}