package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonBoolean;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

public class PythonBooleanConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonBoolean((Boolean) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isBoolean(type);
    }
}