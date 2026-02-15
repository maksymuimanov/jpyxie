package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonBoolean;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

public class PythonBooleanConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonBoolean((Boolean) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isBoolean(type);
    }
}