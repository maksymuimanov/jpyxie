package io.maksymuimanov.python.schema;

import io.maksymuimanov.python.bind.PythonMethodParameter;

import java.util.Set;

public class PythonSchemaBooleanFieldTypeConverter implements PythonSchemaTypeConverter<PythonMethodParameter> {
    public static final String PYTHON_BOOL_TYPE = "bool";

    @Override
    public PythonMethodParameter convert(PythonMethodParameter typed, Class<?> clazz) {
        typed.setType(PYTHON_BOOL_TYPE);
        return typed;
    }

    @Override
    public Set<Class<?>> getSupportedClasses() {
        return Set.of(
                boolean.class, Boolean.class
        );
    }
}
