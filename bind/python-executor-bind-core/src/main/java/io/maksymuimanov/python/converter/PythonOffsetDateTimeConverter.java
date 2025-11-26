package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonOffsetDateTime;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

import java.time.OffsetDateTime;

public class PythonOffsetDateTimeConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonOffsetDateTime((OffsetDateTime) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isOffsetDateTime(type);
    }
}
