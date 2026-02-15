package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonRepresentation;
import org.jspecify.annotations.Nullable;

public interface PythonSerializer {
    PythonRepresentation serialize(@Nullable Object o);

    PythonRepresentation serialize(@Nullable Object o, Class<? extends PythonTypeConverter> converter);
}
