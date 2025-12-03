package io.maksymuimanov.python.serializer;

import io.maksymuimanov.python.bind.PythonNone;
import io.maksymuimanov.python.converter.PythonTypeConverter;
import io.maksymuimanov.python.exception.PythonSerializationException;
import io.maksymuimanov.python.script.PythonRepresentation;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BasicPythonSerializer implements PythonSerializer {
    private final List<PythonTypeConverter> converters;

    public BasicPythonSerializer(Iterable<PythonTypeConverter> converters) {
        this.converters = new LinkedList<>();
        converters.forEach(this.converters::add);
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
