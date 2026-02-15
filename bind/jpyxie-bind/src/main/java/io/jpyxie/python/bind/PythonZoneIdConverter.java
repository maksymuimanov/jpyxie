package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.script.PythonZoneId;
import io.jpyxie.python.util.JavaTypeUtils;
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
