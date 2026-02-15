package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.exception.PythonSerializationException;
import io.maksymuimanov.python.script.PythonNone;
import io.maksymuimanov.python.script.PythonRepresentation;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@ApiStatus.Experimental
public class DictionaryPythonSerializer implements PythonSerializer {
    private final List<PythonTypeConverter> converters;

    public DictionaryPythonSerializer(List<PythonTypeConverter> converters) {
        this.converters = converters;
        Collections.sort(this.converters);
    }

    @Override
    public PythonRepresentation serialize(@Nullable Object o) {
        try {
            if (o == null) return new PythonNone();
            for (PythonTypeConverter typeConverter : converters) {
                Class<?> clazz = o.getClass();
                if (typeConverter.supports(clazz)) return typeConverter.convert(o, this);
            }
            return new PythonNone();
        } catch (Exception e) {
            throw new PythonSerializationException(e);
        }
    }

    @Override
    public PythonRepresentation serialize(@Nullable Object o, Class<? extends PythonTypeConverter> typeConverterClass) {
        try {
            if (o == null) return new PythonNone();
            for (PythonTypeConverter typeConverter : converters) {
                if (!typeConverterClass.equals(typeConverter.getClass())) continue;
                Class<?> clazz = o.getClass();
                if (typeConverter.supports(clazz)) return typeConverter.convert(o, this);
            }
            PythonTypeConverter typeConverter = typeConverterClass.getConstructor().newInstance();
            return typeConverter.convert(o, this);
        } catch (Exception e) {
            throw new PythonSerializationException(e);
        }
    }
}
