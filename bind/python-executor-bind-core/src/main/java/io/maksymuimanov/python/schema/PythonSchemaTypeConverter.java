package io.maksymuimanov.python.schema;

import io.maksymuimanov.python.bind.PythonTyped;

import java.util.Set;

public interface PythonSchemaTypeConverter<T extends PythonTyped> {
    T convert(T typed, Class<?> clazz);

    Set<Class<?>> getSupportedClasses();
}
