package io.jpyxie.python.bind;

import io.jpyxie.python.script.PythonDuration;
import io.jpyxie.python.script.PythonRepresentation;
import io.jpyxie.python.util.JavaTypeUtils;
import org.jspecify.annotations.Nullable;

import java.time.Duration;

public class PythonDurationConverter implements PythonTypeConverter {
    @Override
    public PythonRepresentation convert(@Nullable Object value, PythonSerializer pythonSerializer) {
        return new PythonDuration((Duration) value);
    }

    @Override
    public boolean supports(Class<?> type) {
        return JavaTypeUtils.isDuration(type);
    }
}
