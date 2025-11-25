package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonString;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

public class PythonStringConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonString(value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isString(type);
    }
}
