package io.maksymuimanov.python.deconverter;

import io.maksymuimanov.python.bind.PythonBigDecimal;
import io.maksymuimanov.python.deserializer.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonUnsupportedTypeDeconversionException;
import io.maksymuimanov.python.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

public class PythonBigDecimalDeconverter extends AbstractPythonTypeDeconverter<PythonBigDecimal, BigDecimal> {
    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T deconvert(PythonBigDecimal pythonRepresentation, Class<T> clazz, PythonDeserializer pythonDeserializer) {
        BigDecimal number = pythonRepresentation.getValue();
        if (BigDecimal.class.equals(clazz)) {
            return (T) number;
        } else {
            throw new PythonUnsupportedTypeDeconversionException(clazz + " is not supported");
        }
    }

    @Override
    public PythonBigDecimal resolve(CharSequence value, PythonDeserializer pythonDeserializer) {
        BigDecimal result = this.getValue(value, (key) -> new BigDecimal(key.toString()));
        return new PythonBigDecimal(result);
    }

    @Override
    public boolean matches(CharSequence value) {
        return StringUtils.isFloat(value);
    }

    @Override
    public int getPriority() {
        return LOW_PRIORITY;
    }
}
