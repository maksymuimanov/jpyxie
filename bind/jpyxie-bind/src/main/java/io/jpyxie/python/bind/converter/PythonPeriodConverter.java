package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.bind.JavaTypeUtils;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonPeriod;
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
