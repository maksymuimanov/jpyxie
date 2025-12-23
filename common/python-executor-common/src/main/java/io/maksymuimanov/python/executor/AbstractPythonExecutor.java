package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPythonExecutor<C> implements PythonExecutor {
    @Override
    @Nullable
    public abstract <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription);

    @Override
    public abstract Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions);

    protected Map<String, @Nullable Object> getResultMap(Iterable<PythonResultDescription<?>> resultDescriptions, C resultContainer) {
        Map<String, @Nullable Object> result = new HashMap<>();
        for (PythonResultDescription<?> resultDescription : resultDescriptions) {
            String fieldName = resultDescription.fieldName();
            Object resultValue = this.getResult(resultDescription, resultContainer);
            result.put(fieldName, resultValue);
        }

        return result;
    }

    @Nullable
    protected abstract <R> R getResult(PythonResultDescription<R> resultDescription, C resultContainer);
}
