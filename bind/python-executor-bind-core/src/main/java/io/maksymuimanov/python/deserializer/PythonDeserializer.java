package io.maksymuimanov.python.deserializer;

import io.maksymuimanov.python.deconverter.PythonTypeDeconverter;
import io.maksymuimanov.python.script.PythonRepresentation;

public interface PythonDeserializer {
    <T> T deserialize(CharSequence data, Class<T> clazz);

    <T> T deserialize(CharSequence data, Class<T> clazz, Class<? extends PythonTypeDeconverter> deconverter);

    PythonRepresentation represent(CharSequence data);

    PythonRepresentation represent(CharSequence data, Class<? extends PythonTypeDeconverter> deconverter);
}
