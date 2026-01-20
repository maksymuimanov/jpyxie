package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.executor.PythonResultRequirement;
import jep.Interpreter;
import org.jspecify.annotations.Nullable;

public class JepPythonDeserializer implements PythonDeserializer<Interpreter> {
    @Override
    @Nullable
    public <T> T deserialize(Interpreter from, PythonResultRequirement<T> resultRequirement) {
        return from.getValue(resultRequirement.name(), resultRequirement.type());
    }
}
