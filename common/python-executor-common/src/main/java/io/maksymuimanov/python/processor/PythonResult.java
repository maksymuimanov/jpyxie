package io.maksymuimanov.python.processor;

import org.jspecify.annotations.Nullable;

import java.util.*;

public class PythonResult<R> {
    private final String name;
    @Nullable
    private final R body;
    private final Class<R> type;

    public static List<PythonResult<?>> allOf(Map<String, @Nullable Object> resultMap) {
        List<PythonResult<?>> responses = new ArrayList<>();
        for (Map.Entry<String, @Nullable Object> entry : resultMap.entrySet()) {
            String name = entry.getKey();
            Object body = entry.getValue();
            PythonResult<?> response = PythonResult.of(name, body);
            responses.add(response);
        }
        return responses;
    }

    @SuppressWarnings("unchecked")
    public static <R> PythonResult<R> of(String name, @Nullable R body) {
        return new PythonResult<>(name, body, body == null ? (Class<R>) Void.class : (Class<R>) body.getClass());
    }

    private PythonResult(String name, @Nullable R body, Class<R> type) {
        this.name = name;
        this.body = body;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public R getBody() {
        return body;
    }

    public Optional<R> getBodyOptional() {
        return Optional.ofNullable(this.getBody());
    }

    public Class<R> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PythonResult<?> that = (PythonResult<?>) o;
        return Objects.equals(this.getBody(), that.getBody()) && Objects.equals(this.getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getBody(), this.getType());
    }

    @Override
    public String toString() {
        String stringBuilder = "PythonResult{" + "name=" + name +
                ", body=" + body +
                ", type=" + type.getName() +
                '}';
        return stringBuilder;
    }
}