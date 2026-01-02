package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.common.Prioritized;
import io.maksymuimanov.python.script.PythonRepresentation;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface PythonTypeConverter extends Prioritized {
    PythonRepresentation convert(Object value, PythonSerializer pythonSerializer);

    boolean supports(Class<?> type);
}