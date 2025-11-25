package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.bind.JavaTypeUtils;
import io.maksymuimanov.python.bind.PythonQueue;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

import java.util.ArrayDeque;
import java.util.Queue;

public class PythonQueueConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(Object value, PythonSerializer pythonSerializer) {
        Queue<?> queue = (Queue<?>) value;
        Queue<PythonRepresentation> representations = new ArrayDeque<>(queue.size());
        for (Object element : queue) {
            representations.add(pythonSerializer.serialize(element));
        }
        return new PythonQueue(representations);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isQueue(type);
    }
}
