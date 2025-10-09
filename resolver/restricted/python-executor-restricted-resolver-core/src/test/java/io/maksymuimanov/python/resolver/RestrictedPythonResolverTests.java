package io.maksymuimanov.python.resolver;

import io.maksymuimanov.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static io.maksymuimanov.python.constant.TestConstants.*;

class RestrictedPythonResolverTests {
    private static final PythonResolver RESTRICTED_PYTHON_RESOLVER = new RestrictedPythonResolver(IMPORT_LINE, CODE_VARIABLE_NAME, LOCAL_VARIABLES_NAME, RESULT_APPEARANCE, PRINTED);

    @ParameterizedTest
    @ValueSource(strings = {
            SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3,
            RESULT_SCRIPT_0, RESULT_SCRIPT_1, RESULT_SCRIPT_2, RESULT_SCRIPT_3
    })
    void testResolve(String script) {
        PythonScript pythonScript = new PythonScript(script);
        RESTRICTED_PYTHON_RESOLVER.resolve(pythonScript, Map.of());
        Assertions.assertTrue(pythonScript.containsImport(IMPORT_LINE));
        Assertions.assertTrue(pythonScript.containsDeepCode(CODE_VARIABLE_NAME));
        Assertions.assertTrue(pythonScript.containsDeepCode(LOCAL_VARIABLES_NAME));
        Assertions.assertTrue(pythonScript.containsCode("_print_ = PrintCollector"));
    }
}
