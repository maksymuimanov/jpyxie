package io.maksymuimanov.python.schema;

import io.maksymuimanov.python.bind.PythonMethodParameter;

import java.util.Set;

public class PythonSchemaIntFieldTypeConverter implements PythonSchemaTypeConverter<PythonMethodParameter> {
    public static final String PYTHON_INT_TYPE = "int";

    @Override
    public PythonMethodParameter convert(PythonMethodParameter typed, Class<?> clazz) {
        typed.setType(PYTHON_INT_TYPE);
        return typed;
    }

    @Override
    public Set<Class<?>> getSupportedClasses() {
        return Set.of(
                byte.class, Byte.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class
        );
    }
}
