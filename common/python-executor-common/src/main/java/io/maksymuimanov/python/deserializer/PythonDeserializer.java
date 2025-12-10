package io.maksymuimanov.python.deserializer;

import io.maksymuimanov.python.deconverter.PythonTypeDeconverter;
import io.maksymuimanov.python.script.PythonRepresentation;

public interface PythonDeserializer {
    Object deserialize(PythonRepresentation data);

    <T> T deserialize(PythonRepresentation data, Class<T> clazz);

    <T> T deserialize(PythonRepresentation data, Class<T> clazz, Class<? extends PythonTypeDeconverter> deconverter);

    Object deserialize(CharSequence data);

    <T> T deserialize(CharSequence data, Class<T> clazz);

    <T> T deserialize(CharSequence data, Class<T> clazz, Class<? extends PythonTypeDeconverter> deconverter);

    PythonRepresentation represent(CharSequence data);

    PythonRepresentation represent(CharSequence data, Class<? extends PythonTypeDeconverter> deconverter);
}
