package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonBigDecimal;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

import java.math.BigDecimal;

public class PythonBigDecimalConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonBigDecimal((BigDecimal) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isBigDecimal(type);
    }
}
