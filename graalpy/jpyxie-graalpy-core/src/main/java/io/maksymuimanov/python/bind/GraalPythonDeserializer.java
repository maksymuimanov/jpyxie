package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.executor.PythonResultRequirement;
import org.graalvm.polyglot.Value;
import org.jspecify.annotations.Nullable;

public class GraalPythonDeserializer implements PythonDeserializer<Value> {
    @Override
    @Nullable
    public <T> T deserialize(Value from, PythonResultRequirement<T> resultRequirement) {
        return from.getMember(resultRequirement.name())
                .as(resultRequirement.type());
    }
}
