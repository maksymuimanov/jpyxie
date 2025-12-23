package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.script.PythonScript;
import jep.Interpreter;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class JepPythonExecutor extends InterpretablePythonExecutor<Interpreter, Interpreter> {
    public JepPythonExecutor(PythonInterpreterProvider<Interpreter> interpreterProvider) {
        super(interpreterProvider);
    }

    @Override
    protected @Nullable <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription, Interpreter interpreter) throws Exception {
        interpreter.exec(script.toPythonString());
        return this.getResult(resultDescription, interpreter);
    }

    @Override
    protected Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Interpreter interpreter) throws Exception {
        interpreter.exec(script.toPythonString());
        return this.getResultMap(resultDescriptions, interpreter);
    }

    @Override
    @Nullable
    protected <R> R getResult(PythonResultDescription<R> resultDescription, Interpreter resultContainer) {
        return resultDescription.getValue((type, fieldName) -> resultContainer.getValue(fieldName, type));
    }
}