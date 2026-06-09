package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.bind.JavaTypeUtils;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonInstant;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.Date;

public class PythonDateConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        Instant instant = ((Date) object).toInstant();
        return new PythonInstant(instant);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isDate(type);
    }
}
