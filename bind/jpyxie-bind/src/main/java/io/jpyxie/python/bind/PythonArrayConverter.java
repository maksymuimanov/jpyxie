package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonRepresentation;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PythonArrayConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        List<Object> list = new ArrayList<>();
        int length = Array.getLength(value);
        for (int i = 0; i < length; i++) {
            Object element = Array.get(value, i);
            list.add(element);
        }

        return pythonSerializer.serialize(list, PythonListConverter.class);
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.isArray();
    }
}
