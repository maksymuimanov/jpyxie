package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonBigInteger;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

import java.math.BigInteger;

public class PythonBigIntegerConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        return new PythonBigInteger((BigInteger) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isBigInteger(type);
    }
}
