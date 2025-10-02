package io.w4t3rcs.python;

import io.w4t3rcs.python.config.PythonAutoConfiguration;
import io.w4t3rcs.python.file.BasicPythonFileReader;
import io.w4t3rcs.python.file.PythonFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = {PythonAutoConfiguration.class})
class PythonFileReaderConfigurationTests {
    @Autowired
    private PythonFileReader pythonFileReader;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(BasicPythonFileReader.class, pythonFileReader);
    }
}
