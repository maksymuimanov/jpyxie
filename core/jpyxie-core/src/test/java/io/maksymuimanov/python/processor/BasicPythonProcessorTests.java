package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.file.PythonFileReader;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.maksymuimanov.python.constant.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class BasicPythonProcessorTests {
    @InjectMocks
    private BasicPythonProcessor pythonProcessor;
    @Mock
    private PythonFileReader pythonFileReader;
    @Mock
    private PythonExecutor pythonExecutor;
    @Mock
    private PythonResolverHolder pythonResolverHolder;

    @ParameterizedTest
    @ValueSource(strings = {
            SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3,
            RESULT_SCRIPT_0, RESULT_SCRIPT_1, RESULT_SCRIPT_2, RESULT_SCRIPT_3,
            SPELYTHON_SCRIPT_0, SPELYTHON_SCRIPT_1,
            COMPOUND_SCRIPT_0, COMPOUND_SCRIPT_1
    })
    void testProcess(String script) {
        PythonScript pythonScript = new PythonScript(script);

        Mockito.when(pythonResolverHolder.resolveAll(pythonScript, EMPTY_ARGUMENTS)).thenReturn(pythonScript);
        Mockito.when(pythonExecutor.execute(pythonScript, STRING_CLASS)).thenReturn(OK_RESPONSE);

        String processed = pythonProcessor.process(script, STRING_CLASS, EMPTY_ARGUMENTS).body();
        Assertions.assertEquals(OK, processed);
    }
}
