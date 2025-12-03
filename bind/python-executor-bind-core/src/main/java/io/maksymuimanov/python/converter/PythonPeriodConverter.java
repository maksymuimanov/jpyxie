package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonPeriod;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import io.maksymuimanov.python.util.JavaTypeUtils;

import java.time.Period;

public class PythonPeriodConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonPeriod((Period) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isPeriod(type);
    }
}
