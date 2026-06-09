package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.bind.JavaTypeUtils;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonInt;
import org.jspecify.annotations.Nullable;

public class PythonIntConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        return new PythonInt((Number) object);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isInteger(type);
    }
}
