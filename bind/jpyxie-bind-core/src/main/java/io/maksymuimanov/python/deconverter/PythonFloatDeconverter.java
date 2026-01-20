package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.bind.PythonFloat;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

public class PythonFloatDeconverter extends AbstractPythonTypeDeconverter<PythonFloat, Number> {
    @Override
    public @Nullable Object deconvert(PythonFloat pythonRepresentation, PythonDeserializer pythonDeserializer) {
        return this.deconvert(pythonRepresentation, Number.class, pythonDeserializer);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonFloat pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        Number number = pythonRepresentation.getValue();
        if (float.class.equals(clazz) || Float.class.equals(clazz)) {
            return (T) ((Float) number.floatValue());
        } else if (Double.class.equals(clazz) || double.class.equals(clazz)) {
            return (T) ((Double) number.doubleValue());
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonFloat resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        Number result = this.getValue(value, key -> Double.parseDouble(key.toString()));
        return new PythonFloat(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return this.matches(value, StringUtils::isFloat, cs -> {
            try {
                Number doubleValue = Double.parseDouble(value.toString());
                this.saveValue(value, doubleValue);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }
}
