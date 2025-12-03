package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonInstant;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import io.maksymuimanov.python.util.JavaTypeUtils;

import java.time.Instant;

public class PythonInstantConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonInstant((Instant) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isInstant(type);
    }
}
