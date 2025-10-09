package io.maksymuimanov.python;

import io.maksymuimanov.python.autoconfigure.RestPythonExecutorAutoConfiguration;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.RestPythonExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = RestPythonExecutorAutoConfiguration.class)
class RestPythonExecutorConfigurationTests {
    @Autowired
    private PythonExecutor pythonExecutor;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(RestPythonExecutor.class, pythonExecutor);
    }
}
