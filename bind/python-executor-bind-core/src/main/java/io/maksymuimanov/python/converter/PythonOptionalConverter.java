package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.PythonNone;
import io.maksymuimanov.python.bind.PythonSerializer;
import io.maksymuimanov.python.bind.PythonTypeConverter;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;

import java.util.Optional;

public class PythonOptionalConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        Optional<?> optional = (Optional<?>) value;
        return optional.map(pythonSerializer::serialize).orElse(new PythonNone());
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isOptional(type);
    }
}
