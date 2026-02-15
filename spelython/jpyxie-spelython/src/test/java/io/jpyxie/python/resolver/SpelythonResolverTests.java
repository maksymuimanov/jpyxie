package io.jpyxie.python.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jpyxie.python.script.PythonScript;
import io.jpyxie.python.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import static io.jpyxie.python.constant.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class SpelythonResolverTests {
    @InjectMocks
    private SpelythonResolver spelythonResolver;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        TestUtils.setField(spelythonResolver, "regex", REGEX);
        TestUtils.setField(spelythonResolver, "localVariableIndex", LOCAL_VARIABLE_INDEX);
        TestUtils.setField(spelythonResolver, "positionFromStart", POSITION_FROM_START);
        TestUtils.setField(spelythonResolver, "positionFromEnd", POSITION_FROM_END);
    }

    @ParameterizedTest
    @ValueSource(strings = {SPELYTHON_SCRIPT_0, SPELYTHON_SCRIPT_1})
    void testResolve(String script) throws JsonProcessingException {
        PythonScript pythonScript = new PythonScript(script);
        String expressionValue = "Test String";

        Mockito.lenient()
                .when(objectMapper.writeValueAsString(expressionValue))
                .thenReturn(expressionValue);

        spelythonResolver.resolve(pythonScript, PythonArgumentSpec.create("a", expressionValue).with("b", expressionValue));
        Assertions.assertFalse(pythonScript.containsDeepCode(REGEX));
        Assertions.assertTrue(pythonScript.containsDeepCode("json.loads('" + expressionValue + "')"));
    }
}
