package io.w4t3rcs.python.file;

import io.w4t3rcs.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.w4t3rcs.python.constant.TestConstants.FILE_HANDLER;
import static io.w4t3rcs.python.constant.TestConstants.FILE_SCRIPT;

class BasicPythonFileReaderTests {
    @Test
    void testReadScript() {
        PythonScript pythonScript = new PythonScript(FILE_SCRIPT);
        FILE_HANDLER.readScript(pythonScript);
        Assertions.assertEquals("print(2 + 2)", pythonScript.toString());
    }
}
