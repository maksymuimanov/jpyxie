package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonDate;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

public class PythonLocalDateConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonDate((LocalDate) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isLocalDate(type);
    }
}
