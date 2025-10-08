package io.w4t3rcs.python.resolver;

import io.w4t3rcs.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static io.w4t3rcs.python.constant.TestConstants.*;

class Py4JResolverTests {
    private static final PythonResolver PY4J_RESOLVER = new Py4JResolver(PY4J_GATEWAY_OBJECT, PY4J_GATEWAY_PROPERTIES, PY4J_IMPORT_LINE);

    @ParameterizedTest
    @ValueSource(strings = {
            SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3
    })
    void testResolve(String script) {
        PythonScript pythonScript = new PythonScript(script);
        PY4J_RESOLVER.resolve(pythonScript, Map.of());
        Assertions.assertTrue(pythonScript.containsImport(PY4J_IMPORT_LINE));
        Assertions.assertTrue(pythonScript.containsCode(PY4J_GATEWAY_OBJECT.formatted((Object[]) PY4J_GATEWAY_PROPERTIES)));
    }
}
