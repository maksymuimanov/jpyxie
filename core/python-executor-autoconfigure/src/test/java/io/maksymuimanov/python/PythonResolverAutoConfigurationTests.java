package io.maksymuimanov.python;

import io.maksymuimanov.python.autoconfigure.PythonResolverAutoConfiguration;
import io.maksymuimanov.python.resolver.BasicPythonResolverHolder;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = PythonResolverAutoConfiguration.class)
class PythonResolverAutoConfigurationTests {
    @Autowired
    private PythonResolverHolder pythonResolverHolder;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(BasicPythonResolverHolder.class, pythonResolverHolder);
    }
}
