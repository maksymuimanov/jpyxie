package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonOffsetDateTime;
import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.OffsetDateTime;

public class PythonOffsetDateTimeConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        return new PythonOffsetDateTime((OffsetDateTime) object);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isOffsetDateTime(type);
    }
}
