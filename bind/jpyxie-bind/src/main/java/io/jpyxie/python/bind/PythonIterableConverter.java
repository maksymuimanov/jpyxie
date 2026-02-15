package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PythonIterableConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        Iterable<?> iterable = (Iterable<?>) value;
        List<Object> list = new ArrayList<>();
        iterable.forEach(list::add);
        return pythonSerializer.serialize(list, PythonListConverter.class);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isIterable(type);
    }

    @Override
    public int getPriority() {
        return LOW_PRIORITY;
    }
}
