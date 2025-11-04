package io.maksymuimanov.python.schema;

import io.maksymuimanov.python.bind.PythonMethodParameter;

import java.util.Set;

public class PythonSchemaFloatFieldTypeConverter implements PythonSchemaTypeConverter<PythonMethodParameter> {
    public static final String PYTHON_FLOAT_TYPE = "float";

    @Override
    public PythonMethodParameter convert(PythonMethodParameter typed, Class<?> clazz) {
        typed.setType(PYTHON_FLOAT_TYPE);
        return typed;
    }

    @Override
    public Set<Class<?>> getSupportedClasses() {
        return Set.of(
                float.class, Float.class,
                double.class, Double.class
        );
    }
}
