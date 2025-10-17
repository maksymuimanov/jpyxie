package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import jep.Interpreter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

@RequiredArgsConstructor
public class JepPythonExecutor implements PythonExecutor {
    private final Interpreter interpreter;
    private final String resultAppearance;

    @Override
    public <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass) {
        try (interpreter) {
            interpreter.runScript(script.toString());
            return resultClass != null && script.containsDeepCode(resultAppearance)
                    ? new PythonExecutionResponse<R>(interpreter.getValue(resultAppearance, resultClass))
                    : new PythonExecutionResponse<R>(null);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }
}