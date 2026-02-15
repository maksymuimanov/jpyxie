package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.script.PythonQueue;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Queue;

public class PythonQueueConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
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
