package io.w4t3rcs.python.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.w4t3rcs.python.properties.SpelythonResolverProperties;
import io.w4t3rcs.python.script.PythonScript;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static io.w4t3rcs.python.constant.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class SpelythonResolverTests {
    @InjectMocks
    private SpelythonResolver spelythonResolver;
    @Mock
    private SpelythonResolverProperties resolverProperties;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(spelythonResolver, "resolverProperties", SPELYTHON_PROPERTIES);
    }

    @ParameterizedTest
    @ValueSource(strings = {SPELYTHON_SCRIPT_0, SPELYTHON_SCRIPT_1})
    void testResolve(String script) throws JsonProcessingException {
        PythonScript pythonScript = new PythonScript(script);
        String expressionValue = "Test String";

        Mockito.lenient()
                .when(objectMapper.writeValueAsString(expressionValue))
                .thenReturn(expressionValue);

        spelythonResolver.resolve(pythonScript, Map.of("a", expressionValue, "b", expressionValue));
        Assertions.assertFalse(pythonScript.containsDeepCode(SPELYTHON_PROPERTIES.regex()));
        Assertions.assertTrue(pythonScript.containsDeepCode("json.loads('" + expressionValue + "')"));
    }
}
