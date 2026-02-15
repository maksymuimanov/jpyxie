package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.representation.PythonZoneId;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.ZoneId;

public class PythonZoneIdConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonZoneId((ZoneId) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isZoneId(type);
    }
}
