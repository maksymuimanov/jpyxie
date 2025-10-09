package io.maksymuimanov.python;

import io.maksymuimanov.python.autoconfigure.RestrictedPythonResolverAutoConfiguration;
import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.RestrictedPythonResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = RestrictedPythonResolverAutoConfiguration.class)
class RestrictedPythonResolverConfigurationTests {
    @Autowired
    private PythonResolver pythonResolver;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(RestrictedPythonResolver.class, pythonResolver);
    }
}
