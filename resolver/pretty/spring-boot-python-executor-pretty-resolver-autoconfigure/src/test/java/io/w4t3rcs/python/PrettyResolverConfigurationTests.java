package io.w4t3rcs.python;

import io.w4t3rcs.python.autoconfigure.PrettyResolverAutoConfiguration;
import io.w4t3rcs.python.resolver.PrettyResolver;
import io.w4t3rcs.python.resolver.PythonResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = PrettyResolverAutoConfiguration.class)
class PrettyResolverConfigurationTests {
    @Autowired
    private PythonResolver pythonResolver;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(PrettyResolver.class, pythonResolver);
    }
}
