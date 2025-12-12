package io.maksymuimanov.python.interpreter;

import org.python.util.PythonInterpreter;

public class JythonInterpreterFactory implements PythonInterpreterFactory<PythonInterpreter> {
    @Override
    public PythonInterpreter create() {
        return new PythonInterpreter();
    }
}
