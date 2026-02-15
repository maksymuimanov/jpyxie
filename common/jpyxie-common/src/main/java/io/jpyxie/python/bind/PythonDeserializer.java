package io.jpyxie.python.bind;

import io.jpyxie.python.executor.PythonResultRequirement;
import org.jspecify.annotations.Nullable;

public interface PythonDeserializer<F> {
    @Nullable
    <T> T deserialize(F from, PythonResultRequirement<T> resultRequirement);
}