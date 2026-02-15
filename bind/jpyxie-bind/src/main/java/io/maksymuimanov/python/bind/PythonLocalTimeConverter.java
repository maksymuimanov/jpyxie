package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.script.PythonTime;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.LocalTime;

public class PythonLocalTimeConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonTime((LocalTime) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isLocalTime(type);
    }
}
