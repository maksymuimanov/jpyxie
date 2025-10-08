package io.w4t3rcs.python.resolver;

import io.w4t3rcs.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static io.w4t3rcs.python.constant.TestConstants.*;

class ResultResolverTests {
    private static final PythonResolver RESULT_RESOLVER = new ResultResolver(REGEX, APPEARANCE, POSITION_FROM_START, POSITION_FROM_END, PRINTED);

    @ParameterizedTest
    @ValueSource(strings = {RESULT_SCRIPT_0, RESULT_SCRIPT_1, RESULT_SCRIPT_2, RESULT_SCRIPT_3})
    void testResolve(String script) {
        PythonScript pythonScript = new PythonScript(script);
        RESULT_RESOLVER.resolve(pythonScript, Map.of());
        Assertions.assertTrue(pythonScript.containsDeepCode(APPEARANCE));
        Assertions.assertTrue(pythonScript.containsCode(APPEARANCE + " = json.loads(json.dumps(test_var))"));
        Assertions.assertTrue(pythonScript.containsCode("print('" + APPEARANCE + "' + json.dumps(" + APPEARANCE + "))"));
    }
}
