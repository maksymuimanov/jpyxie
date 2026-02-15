package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonNone;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class PythonOptionalConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        Optional<?> optional = (Optional<?>) value;
        return optional.map(pythonSerializer::serialize).orElse(new PythonNone());
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isOptional(type);
    }
}
