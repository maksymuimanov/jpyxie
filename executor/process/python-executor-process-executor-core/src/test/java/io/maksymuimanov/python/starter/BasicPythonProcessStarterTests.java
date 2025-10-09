package io.maksymuimanov.python.starter;

import io.maksymuimanov.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.maksymuimanov.python.constant.TestConstants.*;

class BasicPythonProcessStarterTests {
    private static final ProcessStarter PROCESS_STARTER = new BasicPythonProcessStarter("python");

    @ParameterizedTest
    @ValueSource(strings = {SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3})
    void testStart(String script) {
        PythonScript pythonScript = new PythonScript(script);

        Process process = PROCESS_STARTER.start(pythonScript);
        Assertions.assertEquals(0, process.exitValue());
    }
}
