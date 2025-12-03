package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonString;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PythonStringDeconverter extends AbstractPythonTypeDeconverter<PythonString, String> {
    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonString pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        String value = pythonRepresentation.getValue();
        if (String.class.equals(clazz)) {
            return (T) value;
        } else if (StringBuilder.class.equals(clazz)) {
            return (T) new StringBuilder(value);
        } else if (StringBuffer.class.equals(clazz)) {
            return (T) new StringBuffer(value);
        } else if (char[].class.equals(clazz)) {
            return (T) value.toCharArray();
        } else if (List.class.equals(clazz)) {
            List<Character> chars = new ArrayList<>();
            for (int i = 0; i < value.length(); i++) {
                chars.add(value.charAt(i));
            }
            return (T) chars;
        } else if (char.class.equals(clazz) || Character.class.equals(clazz)) {
            return (T) Character.valueOf(value.charAt(0));
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonString resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        String result = this.getValue(value, CharSequence::toString);
        return new PythonString(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return true;
    }

    @Override
    public int getPriority() {
        return MIN_PRIORITY;
    }
}
