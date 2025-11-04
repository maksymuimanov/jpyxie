package io.maksymuimanov.python.schema;

import io.maksymuimanov.python.bind.PythonMethodParameter;

import java.io.Serializable;
import java.util.Set;

public class PythonSchemaSerializableFieldTypeConverter implements PythonSchemaTypeConverter<PythonMethodParameter> {
    @Override
    public PythonMethodParameter convert(PythonMethodParameter typed, Class<?> clazz) {
        typed.setType(clazz.getSimpleName());
        return typed;
    }

    @Override
    public Set<Class<?>> getSupportedClasses() {
        return Set.of(
                Serializable.class
        );
    }
}
