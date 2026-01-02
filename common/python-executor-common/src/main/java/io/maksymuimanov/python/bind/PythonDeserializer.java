package io.maksymuimanov.python.bind;

import org.jspecify.annotations.Nullable;

public interface PythonDeserializer<F> {
    @Nullable
    <T> T deserialize(F from, Class<T> type);
}