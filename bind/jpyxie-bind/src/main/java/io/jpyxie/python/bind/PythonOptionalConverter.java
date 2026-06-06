package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonNone;
import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class PythonOptionalConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        Optional<?> optional = (Optional<?>) object;
        return optional.map(pythonSerializer::serialize).orElse(new PythonNone());
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isOptional(type);
    }
}
