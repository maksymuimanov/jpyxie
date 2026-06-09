package io.jpyxie.python.bind.converter;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.bind.JavaTypeUtils;
import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.bind.PythonTypeConverter;
import io.jpyxie.python.bind.type.PythonZoneId;
import org.jspecify.annotations.Nullable;

import java.time.ZoneId;

public class PythonZoneIdConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer) {
        return new PythonZoneId((ZoneId) object);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isZoneId(type);
    }
}
