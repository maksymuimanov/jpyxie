package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonInt;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import io.maksymuimanov.python.util.JavaTypeUtils;

public class PythonIntConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonInt((Number) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isInteger(type);
    }
}
