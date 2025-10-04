package io.w4t3rcs.python.input.impl;

import io.w4t3rcs.python.input.BasicPythonInputProcessHandler;
import io.w4t3rcs.python.properties.ProcessPythonExecutorProperties;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.w4t3rcs.python.constant.TestConstants.SIMPLE_SCRIPT_3;

@ExtendWith(MockitoExtension.class)
class BasicPythonInputProcessHandlerTests {
    private static final ProcessPythonExecutorProperties EXECUTOR_PROPERTIES = new ProcessPythonExecutorProperties("python3", true, "r4java");
    @InjectMocks
    private BasicPythonInputProcessHandler inputProcessHandler;
    @Mock
    private ProcessPythonExecutorProperties executorProperties;

    @SneakyThrows
    @Test
    void testHandle() {
        Process process = new ProcessBuilder("python3", "-c", SIMPLE_SCRIPT_3).start();
        process.waitFor();
        Assumptions.assumeTrue(process.exitValue() == 0);

        Mockito.when(executorProperties.loggable()).thenReturn(EXECUTOR_PROPERTIES.loggable());
        Mockito.when(executorProperties.resultAppearance()).thenReturn(EXECUTOR_PROPERTIES.resultAppearance());

        String result = inputProcessHandler.handle(process);
        Assertions.assertEquals("4", result);
    }
}
