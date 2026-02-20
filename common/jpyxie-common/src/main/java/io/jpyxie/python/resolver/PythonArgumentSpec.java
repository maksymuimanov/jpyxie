package io.jpyxie.python.resolver;

import io.jpyxie.python.common.MapSpec;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PythonArgumentSpec implements MapSpec<String, Object> {
    private final Map<String, Object> arguments;

    public static PythonArgumentSpec empty() {
        return new PythonArgumentSpec(Collections.emptyMap());
    }

    public static PythonArgumentSpec of(String name, Object value, Object... others) {
        PythonArgumentSpec spec = create();
        spec.with(name, value);
        for (int i = 0; i < others.length; i += 2) {
            spec.with((String) others[i], others[i + 1]);
        }
        return spec;
    }

    public static PythonArgumentSpec of(Map<String, Object> arguments) {
        return create().putAll(arguments);
    }

    public static PythonArgumentSpec of(String name, Object value) {
        return create().with(name, value);
    }

    public static PythonArgumentSpec create() {
        return new PythonArgumentSpec(new HashMap<>());
    }

    private PythonArgumentSpec(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    public Object get(String name) {
        return this.getArguments().get(name);
    }

    public PythonArgumentSpec with(String name, Object value) {
        this.getArguments().put(name, value);
        return this;
    }

    public PythonArgumentSpec putAll(Map<String, Object> arguments) {
        this.getArguments().putAll(arguments);
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(this.getArguments());
    }

    protected Map<String, Object> getArguments() {
        return arguments;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof PythonArgumentSpec entries)) return false;

        return this.getArguments().equals(entries.getArguments());
    }

    @Override
    public int hashCode() {
        return getArguments().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("PythonArgumentSpec{");
        stringBuilder.append("arguments=").append(this.getArguments());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
