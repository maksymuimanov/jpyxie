package io.w4t3rcs.python;

import io.w4t3rcs.python.config.ResultResolverAutoConfiguration;
import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.ResultResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = ResultResolverAutoConfiguration.class)
class ResultResolverConfigurationTests {
    @Autowired
    private PythonResolver pythonResolver;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(ResultResolver.class, pythonResolver);
    }
}
