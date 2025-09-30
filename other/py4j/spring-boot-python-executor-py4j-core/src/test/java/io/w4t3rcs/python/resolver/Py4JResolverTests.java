package io.w4t3rcs.python.resolver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static io.w4t3rcs.python.constant.TestConstants.*;

class Py4JResolverTests {
    @ParameterizedTest
    @ValueSource(strings = {
            SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3
    })
    void testResolve(String script) {
        String resolved = PY4J_RESOLVER.resolve(script, Map.of());
        Assertions.assertTrue(resolved.contains(PY4J_RESOLVER_PROPERTIES.importLine()));
        Assertions.assertTrue(resolved.contains(PY4J_RESOLVER_PROPERTIES.gatewayObject().formatted(PY4J_RESOLVER_PROPERTIES.gatewayProperties()[0])));
    }
}
