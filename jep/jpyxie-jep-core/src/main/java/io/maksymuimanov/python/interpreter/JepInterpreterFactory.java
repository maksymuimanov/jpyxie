package io.maksymuimanov.python.interpreter;

import jep.Interpreter;
import jep.SharedInterpreter;
import jep.SubInterpreter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JepInterpreterFactory implements PythonInterpreterFactory<Interpreter> {
    public static final JepInterpreterType DEFAULT_INTERPRETER_TYPE = JepInterpreterType.SHARED;
    private final JepInterpreterType interpreterType;

    public JepInterpreterFactory() {
        this(DEFAULT_INTERPRETER_TYPE);
    }

    @Override
    public Interpreter create() {
        return switch (interpreterType) {
            case SHARED -> new SharedInterpreter();
            case SUB -> new SubInterpreter();
        };
    }
}
