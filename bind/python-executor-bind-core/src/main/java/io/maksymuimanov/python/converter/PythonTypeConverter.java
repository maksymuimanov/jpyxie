package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

public interface PythonTypeConverter extends Comparable<PythonTypeConverter> {
    int MAX_PRIORITY = Integer.MAX_VALUE;
    int HIGH_PRIORITY = -1000;
    int DEFAULT_PRIORITY = 0;
    int LOW_PRIORITY = 1000;
    int MIN_PRIORITY = Integer.MIN_VALUE;

    PythonRepresentation convert(Object value, PythonSerializer pythonSerializer);

    boolean supports(Class<?> type);

    default int getPriority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    default int compareTo(PythonTypeConverter o) {
        return Integer.compare(o.getPriority(), this.getPriority());
    }
}