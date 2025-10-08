package io.w4t3rcs.python;

import io.w4t3rcs.python.autoconfigure.PythonProcessorAutoConfiguration;
import io.w4t3rcs.python.processor.BasicPythonProcessor;
import io.w4t3rcs.python.processor.PythonProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = PythonProcessorAutoConfiguration.class)
class PythonProcessorAutoConfigurationTests {
    @Autowired
    private PythonProcessor pythonProcessor;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(BasicPythonProcessor.class, pythonProcessor);
    }
}
