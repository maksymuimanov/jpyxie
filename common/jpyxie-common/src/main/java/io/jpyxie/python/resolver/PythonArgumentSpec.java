package io.jpyxie.python.resolver;

import io.jpyxie.python.common.MapSpec;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record PythonArgumentSpec(
        Map<String, Object> delegate
) implements MapSpec<String, Object> {
    private static final PythonArgumentSpec EMPTY = new PythonArgumentSpec(Collections.emptyMap());

    public static PythonArgumentSpec empty() {
        return EMPTY;
    }

    public static PythonArgumentSpec of(String name, Object value, Object... others) {
        PythonArgumentSpec spec = of();
        spec.with(name, value);
        for (int i = 0; i < others.length; i += 2) {
            spec.with((String) others[i], others[i + 1]);
        }
        return spec;
    }

    public static PythonArgumentSpec of(Map<String, Object> arguments) {
        return of().putAll(arguments);
    }

    public static PythonArgumentSpec of(String name, Object value) {
        return of().with(name, value);
    }

    public static PythonArgumentSpec of() {
        return new PythonArgumentSpec(new HashMap<>());
    }

    public Object get(String name) {
        return this.delegate().get(name);
    }

    public PythonArgumentSpec with(String name, Object value) {
        this.delegate().put(name, value);
        return this;
    }

    public PythonArgumentSpec putAll(Map<String, Object> arguments) {
        this.delegate().putAll(arguments);
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(this.delegate());
    }
}
