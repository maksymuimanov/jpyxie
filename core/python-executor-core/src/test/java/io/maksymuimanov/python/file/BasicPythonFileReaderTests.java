package io.maksymuimanov.python.file;

import io.maksymuimanov.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static io.maksymuimanov.python.constant.TestConstants.FILE_SCRIPT;

class BasicPythonFileReaderTests {
    private static final PythonFileReader FILE_READER = new BasicPythonFileReader("/", StandardCharsets.UTF_8);

    @Test
    void testReadScript() {
        PythonScript pythonScript = new PythonScript(FILE_SCRIPT);
        FILE_READER.readScript(pythonScript);
        Assertions.assertEquals("print(2 + 2)", pythonScript.toString());
    }
}
