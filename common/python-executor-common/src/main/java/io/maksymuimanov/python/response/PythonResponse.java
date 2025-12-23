package io.maksymuimanov.python.response;

import org.jspecify.annotations.Nullable;

import java.util.*;

public class PythonResponse<R> {
    private final String name;
    @Nullable
    private final R body;
    private final Class<R> type;

    public static List<PythonResponse<?>> allOf(Map<String, @Nullable Object> resultMap) {
        List<PythonResponse<?>> responses = new ArrayList<>();
        for (Map.Entry<String, @Nullable Object> entry : resultMap.entrySet()) {
            String name = entry.getKey();
            Object body = entry.getValue();
            PythonResponse<?> response = PythonResponse.of(name, body);
            responses.add(response);
        }
        return responses;
    }

    @SuppressWarnings("unchecked")
    public static <R> PythonResponse<R> of(String name, @Nullable R body) {
        return new PythonResponse<>(name, body, body == null ? (Class<R>) Void.class : (Class<R>) body.getClass());
    }

    private PythonResponse(String name, @Nullable R body, Class<R> type) {
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
        PythonResponse<?> that = (PythonResponse<?>) o;
        return Objects.equals(this.getBody(), that.getBody()) && Objects.equals(this.getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getBody(), this.getType());
    }

    @Override
    public String toString() {
        String stringBuilder = "PythonExecutionResponse{" + "name=" + name +
                ", body=" + body +
                ", type=" + type.getName() +
                '}';
        return stringBuilder;
    }
}