package io.w4t3rcs.python;

import io.w4t3rcs.python.autoconfigure.Py4JAutoConfiguration;
import io.w4t3rcs.python.resolver.Py4JResolver;
import io.w4t3rcs.python.resolver.PythonResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import py4j.GatewayServer;

@SpringBootTest
@AutoConfigureJson
@ContextConfiguration(classes = Py4JAutoConfiguration.class)
@TestPropertySource(properties = "spring.python.py4j.enabled=true")
class Py4JConfigurationTests {
    @Autowired
    private GatewayServer gatewayServer;
    @Autowired
    private PythonResolver py4jResolver;

    @Test
    void testMandatoryBeansLoad() {
        Assertions.assertInstanceOf(GatewayServer.class, gatewayServer);
        Assertions.assertInstanceOf(Py4JResolver.class, py4jResolver);
    }
}
