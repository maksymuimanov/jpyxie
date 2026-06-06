package io.jpyxie.python.bind;

import io.jpyxie.python.PythonRepresentation;
import org.jspecify.annotations.Nullable;

public interface PythonSerializer {
    PythonRepresentation serialize(@Nullable Object object);

    PythonRepresentation serialize(@Nullable Object object, Class<? extends PythonTypeConverter> converter);
}
