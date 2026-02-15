package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.script.PythonBigInteger;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

public class PythonBigIntegerConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonBigInteger((BigInteger) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isBigInteger(type);
    }
}
