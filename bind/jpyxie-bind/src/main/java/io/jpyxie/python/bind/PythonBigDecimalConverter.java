package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonBigDecimal;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

public class PythonBigDecimalConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonBigDecimal((BigDecimal) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isBigDecimal(type);
    }
}
