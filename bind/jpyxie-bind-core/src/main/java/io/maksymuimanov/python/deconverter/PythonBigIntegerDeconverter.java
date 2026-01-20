package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonBigInteger;
import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

public class PythonBigIntegerDeconverter extends AbstractPythonTypeDeconverter<PythonBigInteger, BigInteger> {
    @Override
    public @Nullable Object deconvert(PythonBigInteger pythonRepresentation, PythonDeserializer pythonDeserializer) {
        return this.deconvert(pythonRepresentation, BigInteger.class, pythonDeserializer);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonBigInteger pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        BigInteger number = pythonRepresentation.getValue();
        if (BigInteger.class.equals(clazz)) {
            return (T) number;
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonBigInteger resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        BigInteger result = this.getValue(value, key -> new BigInteger(key.toString()));
        return new PythonBigInteger(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return StringUtils.isInteger(value);
    }

    @Override
    public int getPriority() {
        return LOW_PRIORITY;
    }
}
