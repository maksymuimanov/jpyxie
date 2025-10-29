package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import jep.Interpreter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class JepPythonExecutor implements PythonExecutor {
    private final Supplier<Interpreter> interpreterSupplier;
    private final String resultAppearance;

    @Override
    public <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass) {
        try (Interpreter interpreter = interpreterSupplier.get()) {
            interpreter.exec(script.toString());
            R result = resultClass == null || !script.containsDeepCode(resultAppearance)
                    ? null
                    : interpreter.getValue(resultAppearance, resultClass);
            return new PythonExecutionResponse<>(result);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }
}