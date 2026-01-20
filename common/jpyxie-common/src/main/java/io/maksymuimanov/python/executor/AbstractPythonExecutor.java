package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.processor.PythonResultMap;

public abstract class AbstractPythonExecutor<F> implements PythonExecutor {
    private final PythonDeserializer<F> pythonDeserializer;

    protected AbstractPythonExecutor(PythonDeserializer<F> pythonDeserializer) {
        this.pythonDeserializer = pythonDeserializer;
    }

    protected PythonResultMap createResultMap(PythonResultSpec resultSpec, F from) {
        return PythonResultMap.of(resultSpec, requirement ->
                this.getPythonDeserializer().deserialize(from, requirement));
    }

    protected PythonDeserializer<F> getPythonDeserializer() {
        return pythonDeserializer;
    }
}
