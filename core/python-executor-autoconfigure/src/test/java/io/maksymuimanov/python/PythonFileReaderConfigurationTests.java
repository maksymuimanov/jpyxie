package io.maksymuimanov.python;

import io.maksymuimanov.python.autoconfigure.PythonFileReaderAutoConfiguration;
import io.maksymuimanov.python.file.BasicPythonFileReader;
import io.maksymuimanov.python.file.PythonFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = PythonFileReaderAutoConfiguration.class)
class PythonFileReaderConfigurationTests {
    @Autowired
    private PythonFileReader pythonFileReader;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(BasicPythonFileReader.class, pythonFileReader);
    }
}
