package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonBigInteger;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
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
