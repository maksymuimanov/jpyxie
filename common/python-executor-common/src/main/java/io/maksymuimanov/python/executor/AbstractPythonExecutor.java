package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPythonExecutor<C> implements PythonExecutor {
    private final PythonResultFieldNameProvider resultFieldProvider;

    protected AbstractPythonExecutor(PythonResultFieldNameProvider resultFieldProvider) {
        this.resultFieldProvider = resultFieldProvider;
    }

    @Override
    @Nullable
    public <R> R execute(PythonScript script, Class<R> resultClass) {
        String fieldName = resultFieldProvider.get();
        PythonResultDescription<R> resultDescription = new PythonResultDescription<>(resultClass, fieldName);
        return this.execute(script, resultDescription);
    }

    @Override
    @Nullable
    public abstract  <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription);

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

    protected PythonResultFieldNameProvider getResultFieldProvider() {
        return resultFieldProvider;
    }
}
