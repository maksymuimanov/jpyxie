package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.script.PythonPeriod;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
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
