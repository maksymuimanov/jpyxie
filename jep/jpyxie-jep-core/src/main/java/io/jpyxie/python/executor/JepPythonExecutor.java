package io.jpyxie.python.executor;

import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.script.PythonScript;
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