package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonPeriod;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.Period;

public class PythonPeriodConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonPeriod((Period) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isPeriod(type);
    }
}
