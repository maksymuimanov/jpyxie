package io.maksymuimanov.python.serializer;

import io.maksymuimanov.python.converter.PythonTypeConverter;
import io.maksymuimanov.python.script.PythonRepresentation;
import org.jspecify.annotations.Nullable;

public interface PythonSerializer {
    PythonRepresentation serialize(@Nullable Object o);

    PythonRepresentation serialize(@Nullable Object o, Class<? extends PythonTypeConverter> converter);
}
