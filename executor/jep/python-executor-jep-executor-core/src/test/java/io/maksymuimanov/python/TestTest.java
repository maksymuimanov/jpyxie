package io.maksymuimanov.python;

import io.maksymuimanov.python.executor.JepPythonExecutor;
import io.maksymuimanov.python.script.PythonScript;
import jep.SharedInterpreter;
import org.junit.jupiter.api.Test;

class TestTest {
    @Test
    void test() {
        SharedInterpreter interpreter = new SharedInterpreter();
        JepPythonExecutor jepPythonExecutor = new JepPythonExecutor(interpreter, "r4java");
        jepPythonExecutor.execute(new PythonScript(), null);
    }
}
