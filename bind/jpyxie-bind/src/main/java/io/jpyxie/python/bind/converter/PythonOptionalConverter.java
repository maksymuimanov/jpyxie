package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.bind.JavaTypeUtils;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonNone;
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
