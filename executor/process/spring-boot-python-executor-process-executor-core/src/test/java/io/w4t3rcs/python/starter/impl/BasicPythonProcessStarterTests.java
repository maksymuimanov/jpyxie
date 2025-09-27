package io.w4t3rcs.python.starter.impl;

import io.w4t3rcs.python.file.PythonFileHandler;
import io.w4t3rcs.python.properties.ProcessPythonExecutorProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.w4t3rcs.python.constant.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class BasicPythonProcessStarterTests {
    private static final ProcessPythonExecutorProperties LOCAL_PROPERTIES = new ProcessPythonExecutorProperties("python3", false, "r4java");
    @InjectMocks
    private BasicPythonProcessStarter processStarter;
    @Mock
    private ProcessPythonExecutorProperties executorProperties;
    @Mock
    private PythonFileHandler pythonFileHandler;


    @ParameterizedTest
    @ValueSource(strings = {SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3})
    void testStart(String script) {
        Mockito.when(executorProperties.startCommand()).thenReturn(LOCAL_PROPERTIES.startCommand());
        Mockito.when(pythonFileHandler.isPythonFile(script)).thenReturn(false);

        Process process = processStarter.start(script);
        Assertions.assertEquals(0, process.exitValue());
    }
}
