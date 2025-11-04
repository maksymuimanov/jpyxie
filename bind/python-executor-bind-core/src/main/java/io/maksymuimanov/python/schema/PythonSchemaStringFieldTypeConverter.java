package io.maksymuimanov.python.schema;

import io.maksymuimanov.python.bind.PythonMethodParameter;

import java.util.Set;

public class PythonSchemaStringFieldTypeConverter implements PythonSchemaTypeConverter<PythonMethodParameter> {
    public static final String PYTHON_STR_TYPE = "str";

    @Override
    public PythonMethodParameter convert(PythonMethodParameter typed, Class<?> clazz) {
        typed.setType(PYTHON_STR_TYPE);
        return typed;
    }

    @Override
    public Set<Class<?>> getSupportedClasses() {
        return Set.of(
                char.class, Character.class,
                String.class, CharSequence.class
        );
    }
}
