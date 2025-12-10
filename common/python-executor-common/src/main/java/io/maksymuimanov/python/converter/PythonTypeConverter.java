package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.common.Prioritized;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;

public interface PythonTypeConverter extends Prioritized {
    PythonRepresentation convert(Object value, PythonSerializer pythonSerializer);

    boolean supports(Class<?> type);
}