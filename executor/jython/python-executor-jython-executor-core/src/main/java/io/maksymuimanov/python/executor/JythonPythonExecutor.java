package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.python.util.PythonInterpreter;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class JythonPythonExecutor implements PythonExecutor {
    private final Supplier<PythonInterpreter> interpreterSupplier;
    private final String resultAppearance;

    @Override
    public <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass) {
        try (PythonInterpreter interpreter = interpreterSupplier.get()) {
            interpreter.exec(script.toString());
            R result = resultClass == null || !script.containsDeepCode(resultAppearance)
                    ? null
                    : interpreter.get(resultAppearance, resultClass);
            return new PythonExecutionResponse<>(result);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }
}