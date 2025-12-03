package io.maksymuimanov.python.deserializer;

import io.maksymuimanov.python.deconverter.PythonTypeDeconverter;
import io.maksymuimanov.python.script.PythonRepresentation;

public class BasicPythonDeserializer implements PythonDeserializer {
    @Override
    public <T> T deserialize(CharSequence data, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T deserialize(CharSequence data, Class<T> clazz, Class<? extends PythonTypeDeconverter> deconverter) {
        return null;
    }


    @Override
    public PythonRepresentation represent(CharSequence data) {
        return null;
    }

    @Override
    public PythonRepresentation represent(CharSequence data, Class<? extends PythonTypeDeconverter> deconverter) {
        return null;
    }
}
