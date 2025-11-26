package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonInstant;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

import java.time.Instant;
import java.util.Date;

public class PythonDateConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        Instant instant = ((Date) value).toInstant();
        return new PythonInstant(instant);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isDate(type);
    }
}
