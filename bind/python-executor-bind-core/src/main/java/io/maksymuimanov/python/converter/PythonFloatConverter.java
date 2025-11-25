package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonFloat;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

public class PythonFloatConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonFloat((Number) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isFloat(type);
    }
}
