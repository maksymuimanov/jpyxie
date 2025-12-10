package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonBoolean;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

public class PythonBooleanDeconverter extends AbstractPythonTypeDeconverter<PythonBoolean, Boolean> {
    @Override
    public @Nullable Object deconvert(PythonBoolean pythonRepresentation, PythonDeserializer pythonDeserializer) {
        return this.deconvert(pythonRepresentation, Boolean.class, pythonDeserializer);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonBoolean pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        if (boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
            return (T) pythonRepresentation.getValue();
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonBoolean resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        Boolean result = this.getValue(value, key -> Boolean.parseBoolean(key.toString()));
        return new PythonBoolean(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return StringUtils.isBoolean(value);
    }
}
