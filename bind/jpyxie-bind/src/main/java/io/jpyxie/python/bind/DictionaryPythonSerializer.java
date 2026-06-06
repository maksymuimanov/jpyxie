package io.jpyxie.python.bind;

import io.jpyxie.python.exception.PythonSerializationException;
import io.jpyxie.python.script.PythonNone;
import io.jpyxie.python.PythonRepresentation;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class DictionaryPythonSerializer implements PythonSerializer {
    private final List<PythonTypeConverter> converters;

    public DictionaryPythonSerializer(List<PythonTypeConverter> converters) {
        this.converters = converters;
        Collections.sort(this.converters);
    }

    @Override
    public PythonRepresentation serialize(@Nullable Object object) {
        try {
            if (object == null) return new PythonNone();
            for (PythonTypeConverter typeConverter : converters) {
                Class<?> clazz = object.getClass();
                if (typeConverter.supports(clazz)) return typeConverter.convert(object, this);
            }
            return new PythonNone();
        } catch (Exception e) {
            throw new PythonSerializationException(e);
        }
    }

    @Override
    public PythonRepresentation serialize(@Nullable Object object, Class<? extends PythonTypeConverter> typeConverterClass) {
        try {
            if (object == null) return new PythonNone();
            for (PythonTypeConverter typeConverter : converters) {
                if (!typeConverterClass.equals(typeConverter.getClass())) continue;
                Class<?> clazz = object.getClass();
                if (typeConverter.supports(clazz)) return typeConverter.convert(object, this);
            }
            PythonTypeConverter typeConverter = typeConverterClass.getConstructor().newInstance();
            return typeConverter.convert(object, this);
        } catch (Exception e) {
            throw new PythonSerializationException(e);
        }
    }
}
