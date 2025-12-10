package io.maksymuimanov.python.serializer;

import io.maksymuimanov.python.converter.PythonTypeConverter;
import io.maksymuimanov.python.script.PythonRepresentation;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

@ApiStatus.Experimental
public interface PythonSerializer {
    PythonRepresentation serialize(@Nullable Object o);

    PythonRepresentation serialize(@Nullable Object o, Class<? extends PythonTypeConverter> converter);
}
