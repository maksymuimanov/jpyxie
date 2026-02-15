package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.representation.PythonDuration;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.Duration;

public class PythonDurationConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonDuration((Duration) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isDuration(type);
    }
}
