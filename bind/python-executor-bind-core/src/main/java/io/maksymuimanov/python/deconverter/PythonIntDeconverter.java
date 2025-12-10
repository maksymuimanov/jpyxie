package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonInt;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

public class PythonIntDeconverter extends AbstractPythonTypeDeconverter<PythonInt, Number> {
    @Override
    public @Nullable Object deconvert(PythonInt pythonRepresentation, PythonDeserializer pythonDeserializer) {
        return this.deconvert(pythonRepresentation, Number.class, pythonDeserializer);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonInt pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        Number number = pythonRepresentation.getValue();
        if (byte.class.equals(clazz) || Byte.class.equals(clazz)) {
            return (T) ((Byte) number.byteValue());
        } else if (short.class.equals(clazz) || Short.class.equals(clazz)) {
            return (T) ((Short) number.shortValue());
        } else if (int.class.equals(clazz) || Integer.class.equals(clazz)) {
            return (T) ((Integer) number.intValue());
        } else if (long.class.equals(clazz) || Long.class.equals(clazz)) {
            return (T) ((Long) number.longValue());
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonInt resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        Number result = this.getValue(value, key -> Long.parseLong(key.toString()));
        return new PythonInt(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return this.matches(value, StringUtils::isInteger, cs -> {
            try {
                Number longValue = Long.parseLong(value.toString());
                this.saveValue(value, longValue);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }
}
