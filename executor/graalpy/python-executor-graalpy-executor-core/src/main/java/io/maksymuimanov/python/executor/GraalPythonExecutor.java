package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.interpreter.PythonInterpreterFactory;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.jspecify.annotations.Nullable;

public class GraalPythonExecutor extends InterpretablePythonExecutor<Context> {
    public static final String PYTHON = "python";
    private final String resultAppearance;

    public GraalPythonExecutor(PythonInterpreterFactory<Context> interpreterFactory, String resultAppearance) {
        super(interpreterFactory);
        this.resultAppearance = resultAppearance;
    }

    @Override
    protected <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass, Context interpreter) {
        Value value = interpreter.eval(PYTHON, script.toString());
        R result = resultClass == null || !script.containsDeepCode(resultAppearance)
                ? null
                : value.getMember(resultAppearance).as(resultClass);
        return new PythonExecutionResponse<>(result);
    }
}