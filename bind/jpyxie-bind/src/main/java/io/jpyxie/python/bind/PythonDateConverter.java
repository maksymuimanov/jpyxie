package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonInstant;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.Date;

public class PythonDateConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        Instant instant = ((Date) value).toInstant();
        return new PythonInstant(instant);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isDate(type);
    }
}
