package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonInstant;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

public class PythonInstantConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonInstant((Instant) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isInstant(type);
    }
}
