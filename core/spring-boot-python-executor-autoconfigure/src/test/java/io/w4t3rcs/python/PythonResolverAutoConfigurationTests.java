package io.w4t3rcs.python;

import io.w4t3rcs.python.autoconfigure.PythonResolverAutoConfiguration;
import io.w4t3rcs.python.resolver.BasicPythonResolverHolder;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
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
