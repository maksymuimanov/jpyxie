package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.script.PythonString;
import io.jpyxie.python.util.JavaTypeUtils;
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
