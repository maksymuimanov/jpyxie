package io.maksymuimanov.python.executor;

import org.jspecify.annotations.Nullable;

import java.util.function.BiFunction;

public record PythonResultDescription<T>(Class<T> type, String fieldName) {
    @Nullable
    public T getValue(BiFunction<Class<T>, String, T> function) {
        return this.isVoid() ? null : function.apply(this.type, this.fieldName);
    }

    public boolean isVoid() {
        return Void.class.equals(this.type);
    }
}
