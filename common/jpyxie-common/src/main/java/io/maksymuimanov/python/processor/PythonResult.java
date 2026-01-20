package io.maksymuimanov.python.processor;

import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public class PythonResult<R> {
    private final String name;
    @Nullable
    private final R body;
    private final Class<R> type;

    @SuppressWarnings({"unchecked", "NullableProblems"})
    public static <R> PythonResult<R> present(String name, @Nullable R body) {
        if (body == null) return (PythonResult<R>) absent(name);
        return new PythonResult<>(name, body, (Class<R>) body.getClass());
    }

    public static PythonResult<?> absent(String name) {
        return new PythonResult<>(name, null, Void.class);
    }

    private PythonResult(String name, @Nullable R body, Class<R> type) {
        this.name = name;
        this.body = body;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("DataFlowIssue")
    public R getBodyOrElse(Supplier<R> onAbsent) {
        return this.isPresent() ? this.getBody() : onAbsent.get();
    }

    public boolean isPresent() {
        return !isAbsent();
    }

    public boolean isAbsent() {
        return this.getBody() == null;
    }

    @Nullable
    public R getBody() {
        return body;
    }

    public Class<R> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PythonResult<?> that = (PythonResult<?>) o;
        return Objects.equals(this.getName(), that.getName()) && Objects.equals(this.getBody(), that.getBody()) && Objects.equals(this.getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getBody(), this.getType());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PythonResult{");
        sb.append("name='").append(name).append('\'');
        sb.append(", body=").append(body);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}