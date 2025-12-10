package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonNone;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
import org.jspecify.annotations.Nullable;

public class PythonNoneDeconverter implements PythonTypeDeconverter<PythonNone> {
    @Override
    public @Nullable Object deconvert(PythonNone pythonRepresentation, PythonDeserializer pythonDeserializer) {
        return this.deconvert(pythonRepresentation, Object.class, pythonDeserializer);
    }

    @Override
    @Nullable
    public <T> T deconvert(PythonNone pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        return null;
    }

    @Override
    public PythonNone resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        return new PythonNone();
    }

    @Override
    public boolean matches(CharSequence value) {
        return PythonNone.NONE.contentEquals(value);
    }

    @Override
    public int getPriority() {
        return HIGH_PRIORITY;
    }
}
