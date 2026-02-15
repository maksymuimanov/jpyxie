package io.jpyxie.python.bind;

import io.jpyxie.python.common.Prioritized;
import io.jpyxie.python.script.PythonRepresentation;
import org.jspecify.annotations.Nullable;

public interface PythonTypeConverter extends Prioritized {
    PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer);

    boolean supports(Class<?> type);
}