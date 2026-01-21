package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;
import org.python.util.PythonInterpreter;

public class JythonPythonExecutor extends InterpretablePythonExecutor<PythonInterpreter, PythonInterpreter> {
    public JythonPythonExecutor(PythonDeserializer<PythonInterpreter> pythonDeserializer,
                                PythonInterpreterProvider<PythonInterpreter> interpreterProvider) {
        super(pythonDeserializer, interpreterProvider);
    }

    @Override
    protected PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec, PythonInterpreter interpreter) throws Exception {
        interpreter.exec(script.toPythonString());
        return this.createResultMap(resultSpec, interpreter);
    }
}