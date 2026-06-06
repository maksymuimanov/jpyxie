package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonPeriod;
import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.Period;

public class PythonPeriodConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        return new PythonPeriod((Period) object);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isPeriod(type);
    }
}
