package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.executor.PythonResultRequirement;
import org.jspecify.annotations.Nullable;
import org.python.util.PythonInterpreter;

public class JythonPythonDeserializer implements PythonDeserializer<PythonInterpreter> {
    @Override
    @Nullable
    public <T> T deserialize(PythonInterpreter from, PythonResultRequirement<T> resultRequirement) {
        return from.get(resultRequirement.name(), resultRequirement.type());
    }
}
