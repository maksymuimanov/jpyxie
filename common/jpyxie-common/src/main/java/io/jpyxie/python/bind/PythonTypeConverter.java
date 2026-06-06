package io.jpyxie.python.bind;

import io.jpyxie.python.common.Prioritized;
import io.jpyxie.python.PythonRepresentation;
import org.jspecify.annotations.Nullable;

public interface PythonTypeConverter extends Prioritized {
    PythonRepresentation convert(@Nullable Object object, PythonSerializer pythonSerializer);

    boolean supports(Class<?> type);
}