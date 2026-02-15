package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.representation.PythonInstant;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
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
