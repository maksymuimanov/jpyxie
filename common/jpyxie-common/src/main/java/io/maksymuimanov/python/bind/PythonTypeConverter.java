package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.common.Prioritized;
import io.maksymuimanov.python.script.PythonRepresentation;
import org.jspecify.annotations.Nullable;

public interface PythonTypeConverter extends Prioritized {
    PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer);

    boolean supports(Class<?> type);
}