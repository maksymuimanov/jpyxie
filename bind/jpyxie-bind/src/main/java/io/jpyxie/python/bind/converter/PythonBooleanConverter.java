package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.bind.JavaTypeUtils;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonBoolean;
import org.jspecify.annotations.Nullable;

public class PythonBooleanConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        return new PythonBoolean((Boolean) object);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isBoolean(type);
    }
}