package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonFloat;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

public class PythonFloatConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonFloat((Number) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isFloat(type);
    }
}
