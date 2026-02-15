package io.jpyxie.python;

import io.jpyxie.python.autoconfigure.SpelythonResolverAutoConfiguration;
import io.jpyxie.python.resolver.PythonResolver;
import io.jpyxie.python.resolver.SpelythonResolver;
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
