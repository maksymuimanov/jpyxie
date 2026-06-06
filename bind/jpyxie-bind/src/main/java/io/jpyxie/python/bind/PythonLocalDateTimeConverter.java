package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonDateTime;
import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

public class PythonLocalDateTimeConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        return new PythonDateTime((LocalDateTime) object);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isLocalDateTime(type);
    }
}
