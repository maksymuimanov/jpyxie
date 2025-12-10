package io.maksymuimanov.python.converter;

import io.maksymuimanov.python.common.Prioritized;
import io.maksymuimanov.python.script.PythonRepresentation;
import io.maksymuimanov.python.serializer.PythonSerializer;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface PythonTypeConverter extends Prioritized {
    PythonRepresentation convert(Object value, PythonSerializer pythonSerializer);

    boolean supports(Class<?> type);
}