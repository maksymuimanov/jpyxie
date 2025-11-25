package io.maksymuimanov.python.serializer;

import io.maksymuimanov.python.bind.PythonNone;
import io.maksymuimanov.python.converter.PythonTypeConverter;
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
        return this.serialize(o, null);
    }

    @Override
    public PythonRepresentation serialize(@Nullable Object o, @Nullable Class<? extends PythonTypeConverter> typeConverterClass) {
        if (o == null) return new PythonNone();
        for (PythonTypeConverter typeConverter : converters) {
            if (typeConverterClass != null && !typeConverterClass.equals(typeConverter.getClass())) continue;
            Class<?> clazz = o.getClass();
            if (typeConverter.supports(clazz)) return typeConverter.convert(o, this);
        }

        return new PythonNone();
    }
}
