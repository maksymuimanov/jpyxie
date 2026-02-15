package io.jpyxie.python.file;

import io.jpyxie.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.jpyxie.python.constant.TestConstants.FILE_SCRIPT;

class BasicPythonFileReaderTests {
    private static final PythonFileReader FILE_READER = new BasicPythonFileReader(path -> {
        try {
            return Files.newInputStream(Path.of("src/test/resources/"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }, StandardCharsets.UTF_8);

    @Test
    void testReadScript() {
        PythonScript pythonScript = new PythonScript(FILE_SCRIPT);
        FILE_READER.readScript(pythonScript);
        Assertions.assertEquals("print(2 + 2)", pythonScript.toString());
    }
}
