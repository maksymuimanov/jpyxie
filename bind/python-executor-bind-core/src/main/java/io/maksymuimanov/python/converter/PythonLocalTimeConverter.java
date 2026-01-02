package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonSerializer;
import io.maksymuimanov.python.bind.PythonTime;
import io.maksymuimanov.python.bind.PythonTypeConverter;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;

import java.time.LocalTime;

public class PythonLocalTimeConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonTime((LocalTime) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isLocalTime(type);
    }
}
