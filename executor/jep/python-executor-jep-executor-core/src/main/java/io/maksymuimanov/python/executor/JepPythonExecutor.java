package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.interpreter.PythonInterpreterFactory;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import jep.Interpreter;
import org.jspecify.annotations.Nullable;

public class JepPythonExecutor extends InterpretablePythonExecutor<Interpreter> {
    private final String resultAppearance;

    public JepPythonExecutor(PythonInterpreterFactory<Interpreter> interpreterFactory, String resultAppearance) {
        super(interpreterFactory);
        this.resultAppearance = resultAppearance;
    }

    @Override
    protected <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass, Interpreter interpreter) {
        interpreter.exec(script.toString());
        R result = resultClass == null || !script.containsDeepCode(resultAppearance)
                ? null
                : interpreter.getValue(resultAppearance, resultClass);
        return new PythonExecutionResponse<>(result);
    }
}