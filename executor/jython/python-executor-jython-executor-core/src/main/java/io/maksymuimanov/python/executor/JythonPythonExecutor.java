package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.interpreter.PythonInterpreterConsumer;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;
import org.python.util.PythonInterpreter;

public class JythonPythonExecutor extends InterpretablePythonExecutor<PythonInterpreter> {
    private final String resultAppearance;

    public JythonPythonExecutor(PythonInterpreterConsumer<PythonInterpreter> interpreterConsumer, String resultAppearance) {
        super(interpreterConsumer);
        this.resultAppearance = resultAppearance;
    }

    @Override
    protected <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass, PythonInterpreter interpreter) throws Exception {
        interpreter.exec(script.toPythonString());
        R result = resultClass == null || !script.containsDeepCode(resultAppearance)
                ? null
                : interpreter.get(resultAppearance, resultClass);
        return new PythonExecutionResponse<>(result);
    }
}