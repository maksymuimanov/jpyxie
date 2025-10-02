package io.w4t3rcs.python.resolver;

import io.w4t3rcs.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static io.w4t3rcs.python.constant.TestConstants.*;

class RestrictedPythonResolverTests {
    @ParameterizedTest
    @ValueSource(strings = {
            SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3,
            RESULT_SCRIPT_0, RESULT_SCRIPT_1, RESULT_SCRIPT_2, RESULT_SCRIPT_3
    })
    void testResolve(String script) {
        PythonScript pythonScript = new PythonScript(script);
        RESTRICTED_PYTHON_RESOLVER.resolve(pythonScript, Map.of());
        Assertions.assertTrue(pythonScript.containsImport(RESTRICTED_PYTHON_PROPERTIES.importLine()));
        Assertions.assertTrue(pythonScript.containsDeepCode(RESTRICTED_PYTHON_PROPERTIES.codeVariableName()));
        Assertions.assertTrue(pythonScript.containsDeepCode(RESTRICTED_PYTHON_PROPERTIES.localVariablesName()));
        Assertions.assertTrue(pythonScript.containsCode("_print_ = PrintCollector"));
    }
}
