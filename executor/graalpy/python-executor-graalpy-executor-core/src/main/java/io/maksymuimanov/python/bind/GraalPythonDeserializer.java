package io.maksymuimanov.python.bind;

import org.graalvm.polyglot.Value;
import org.jspecify.annotations.Nullable;

public class GraalPythonDeserializer implements PythonDeserializer<Value> {
    @Override
    @Nullable
    public <T> T deserialize(Value from, Class<T> type) {
        return from.as(type);
    }
}
