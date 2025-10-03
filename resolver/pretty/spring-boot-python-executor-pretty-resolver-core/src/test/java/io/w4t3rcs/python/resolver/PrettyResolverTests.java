package io.w4t3rcs.python.resolver;

import io.w4t3rcs.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static io.w4t3rcs.python.constant.TestConstants.*;

class PrettyResolverTests {
    @ParameterizedTest
    @ValueSource(strings = {SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3})
    void testResolve(String script) {
        PythonScript pythonScript = new PythonScript(script);
        Assertions.assertDoesNotThrow(() -> {
            PRETTY_RESOLVER.resolve(pythonScript, Map.of());
        });
        System.out.println(pythonScript);
    }
}
