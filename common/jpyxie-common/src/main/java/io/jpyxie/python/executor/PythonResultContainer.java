package io.jpyxie.python.executor;

import io.jpyxie.python.common.MapSpec;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PythonResultContainer implements MapSpec<String, Object> {
    private final Map<String, @Nullable Object> results;

    public static PythonResultContainer of(Map<String, @Nullable Object> results) {
        Map<String, @Nullable Object> resultMap = Map.copyOf(results);
        return new PythonResultContainer(resultMap);
    }

    public static PythonResultContainer of() {
        return new PythonResultContainer(new HashMap<>());
    }

    private PythonResultContainer(Map<String, @Nullable Object> results) {
        this.results = results;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) this.results.get(name);
    }

    public boolean isEmpty() {
        return this.results.isEmpty();
    }

    public Map<String, @Nullable Object> toMap() {
        return Collections.unmodifiableMap(results);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("PythonResultContainer{");
        stringBuilder.append("results=").append(results);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
