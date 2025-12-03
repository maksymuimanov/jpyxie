package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonDuration;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import io.maksymuimanov.python.util.JavaTypeUtils;

import java.time.Duration;

public class PythonDurationConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonDuration((Duration) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isDuration(type);
    }
}
