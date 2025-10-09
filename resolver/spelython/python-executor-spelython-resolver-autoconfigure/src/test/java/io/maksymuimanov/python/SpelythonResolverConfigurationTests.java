package io.maksymuimanov.python;

import io.maksymuimanov.python.autoconfigure.SpelythonResolverAutoConfiguration;
import io.maksymuimanov.python.resolver.PythonResolver;
import io.maksymuimanov.python.resolver.SpelythonResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = SpelythonResolverAutoConfiguration.class)
class SpelythonResolverConfigurationTests {
    @Autowired
    private PythonResolver pythonResolver;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(SpelythonResolver.class, pythonResolver);
    }
}
