package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;
import jep.Interpreter;

public class JepPythonExecutor extends InterpretablePythonExecutor<Interpreter, Interpreter> {
    public JepPythonExecutor(PythonDeserializer<Interpreter> pythonDeserializer,
                             PythonInterpreterProvider<Interpreter> interpreterProvider) {
        super(pythonDeserializer, interpreterProvider);
    }

    @Override
    protected PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec, Interpreter interpreter) throws Exception {
        interpreter.exec(script.toPythonString());
        return this.createResultMap(resultSpec, interpreter);
    }
}