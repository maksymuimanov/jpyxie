package io.maksymuimanov.python.deserializer;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.bind.PythonTypeDeconverter;
import io.maksymuimanov.python.script.PythonRepresentation;

public class BasicPythonDeserializer implements PythonDeserializer {
    @Override
    public Object deserialize(PythonRepresentation data) {
        return null;
    }

    @Override
    public <T> T deserialize(PythonRepresentation data, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T deserialize(PythonRepresentation data, Class<T> clazz, Class<? extends PythonTypeDeconverter> deconverter) {
        return null;
    }

    @Override
    public Object deserialize(CharSequence data) {
        return null;
    }

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
