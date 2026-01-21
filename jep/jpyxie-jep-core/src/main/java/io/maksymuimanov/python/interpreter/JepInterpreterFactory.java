package io.maksymuimanov.python.interpreter;

import jep.Interpreter;
import jep.SharedInterpreter;
import jep.SubInterpreter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JepInterpreterFactory implements PythonInterpreterFactory<Interpreter> {
    private final JepInterpreterType interpreterType;

    @Override
    public Interpreter create() {
        return switch (interpreterType) {
            case SHARED -> new SharedInterpreter();
            case SUB -> new SubInterpreter();
        };
    }
}
