package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.common.Prioritized;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
import io.maksymuimanov.python.script.PythonRepresentation;
import org.jspecify.annotations.Nullable;

public interface PythonTypeDeconverter<R extends PythonRepresentation> extends Prioritized {
    @Nullable
    <T> T deconvert(R pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer);

    R resolve(CharSequence value, PythonDeserializer pythonDeserializer);

    boolean matches(CharSequence value);
}