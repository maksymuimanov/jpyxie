package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonZoneId;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

import java.time.ZoneId;

public class PythonZoneIdConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonZoneId((ZoneId) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isZoneId(type);
    }
}
