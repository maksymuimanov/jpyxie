package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.representation.PythonString;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

public class PythonStringConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonString(value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isString(type);
    }
}
