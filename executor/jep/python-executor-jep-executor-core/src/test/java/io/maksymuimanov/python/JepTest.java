package io.maksymuimanov.python;

import io.maksymuimanov.python.executor.JepPythonExecutor;
import io.maksymuimanov.python.script.PythonScript;
import jep.MainInterpreter;
import jep.SharedInterpreter;
import org.junit.jupiter.api.Test;

public class JepTest {
    @Test
    void test() {
        System.load("C:\\Users\\uiman\\AppData\\Local\\Python\\pythoncore-3.14-64\\python314.dll");
        MainInterpreter.setJepLibraryPath("C:\\Users\\uiman\\AppData\\Local\\Python\\pythoncore-3.14-64\\Lib\\site-packages\\jep\\jep.dll");

        JepPythonExecutor jepPythonExecutor = new JepPythonExecutor(SharedInterpreter::new, "res");
        PythonScript script = new PythonScript()
                .getBuilder()
                .appendCode("print('Hello World')")
                .appendCode("res = 'yes'")
                .build();
        System.out.println(jepPythonExecutor.execute(script, String.class).body());
    }
}
