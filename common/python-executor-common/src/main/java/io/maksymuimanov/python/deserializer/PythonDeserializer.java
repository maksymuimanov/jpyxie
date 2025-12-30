package io.maksymuimanov.python.deserializer;

import org.jspecify.annotations.Nullable;

public interface PythonDeserializer<C> {
    @Nullable
    <T> T deserialize(C valueContainer, Class<T> clazz);
}
