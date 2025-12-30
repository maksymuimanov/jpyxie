package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.common.MapSpec;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class PythonResultSpec implements MapSpec<String, Class<?>> {
    private final Map<String, Class<?>> requirements;

    public static PythonResultSpec empty() {
        return new PythonResultSpec(Collections.emptyMap());
    }

    public static PythonResultSpec of(String name, Class<?> type) {
        return create().require(name, type);
    }

    public static PythonResultSpec create() {
        return new PythonResultSpec(new HashMap<>());
    }

    private PythonResultSpec(Map<String, Class<?>> requirements) {
        this.requirements = requirements;
    }

    public PythonResultSpec require(String name, Class<?> type) {
        this.getRequirements().put(name, type);
        return this;
    }

    public PythonResultContainer collect(BiFunction<String, Class<?>, @Nullable Object> objectGetter) {
        if (this.isEmpty()) return PythonResultContainer.of();
        Map<String, @Nullable Object> result = new HashMap<>();
        this.forEach((name, type) -> {
            Object element = objectGetter.apply(name, type);
            result.put(name, element);
        });

        return PythonResultContainer.of(result);
    }

    @Override
    public Map<String, Class<?>> toMap() {
        return Collections.unmodifiableMap(this.getRequirements());
    }

    protected Map<String, Class<?>> getRequirements() {
        return requirements;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("PythonResultSpec{");
        stringBuilder.append("requirements=").append(this.getRequirements());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
