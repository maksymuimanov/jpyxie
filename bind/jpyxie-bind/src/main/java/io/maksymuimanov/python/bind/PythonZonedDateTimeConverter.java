package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.representation.PythonZonedDateTime;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.ZonedDateTime;

public class PythonZonedDateTimeConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonZonedDateTime((ZonedDateTime) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isZonedDateTime(type);
    }
}
