package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonInt;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

public class PythonIntConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonInt((Number) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isInteger(type);
    }
}
