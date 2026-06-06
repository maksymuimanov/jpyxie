package io.jpyxie.python.processor;

import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public record PythonResult<R>(
        String name,
        @Nullable R body,
        Class<R> type
) {
    @SuppressWarnings({"unchecked", "NullableProblems"})
    public static <R> PythonResult<R> present(String name, @Nullable R body) {
        if (body == null) return (PythonResult<R>) absent(name);
        return new PythonResult<>(name, body, (Class<R>) body.getClass());
    }

    public static PythonResult<?> absent(String name) {
        return new PythonResult<>(name, null, Void.class);
    }

    public R getBodyOrElse(R other) {
        return Optional.ofNullable(this.body())
                .orElse(other);
    }

    public R getBodyOrElse(Supplier<R> supplier) {
        return Optional.ofNullable(this.body())
                .orElseGet(supplier);
    }

    public boolean isPresent() {
        return !this.isAbsent();
    }

    public boolean isAbsent() {
        return this.body() == null;
    }
}