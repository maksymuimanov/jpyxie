package io.jpyxie.python.executor;

import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.script.PythonScript;
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