package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.script.PythonOffsetDateTime;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.OffsetDateTime;

public class PythonOffsetDateTimeConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonOffsetDateTime((OffsetDateTime) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isOffsetDateTime(type);
    }
}
