package io.w4t3rcs.python.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.w4t3rcs.python.finisher.ProcessFinisher;
import io.w4t3rcs.python.input.ProcessHandler;
import io.w4t3rcs.python.script.PythonScript;
import io.w4t3rcs.python.starter.ProcessStarter;
import lombok.SneakyThrows;
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
class ProcessPythonExecutorTests {
    @InjectMocks
    private ProcessPythonExecutor processPythonExecutor;
    @Mock
    private ProcessStarter processStarter;
    @Mock
    private ProcessHandler<String> inputProcessHandler;
    @Mock
    private ProcessHandler<Void> errorProcessHandler;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ProcessFinisher processFinisher;

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {SIMPLE_SCRIPT_0})
    void testExecute(String script) {
        PythonScript pythonScript = new PythonScript(script);
        Process process = new ProcessBuilder("python3", "-c", pythonScript.toString()).start();
        process.waitFor();

        Mockito.when(processStarter.start(pythonScript)).thenReturn(process);
        Mockito.when(inputProcessHandler.handle(process)).thenReturn(OK);
        Mockito.doNothing().when(processFinisher).finish(process);
        Mockito.when(objectMapper.readValue(OK, STRING_CLASS)).thenReturn(OK);

        String executed = processPythonExecutor.execute(pythonScript, STRING_CLASS).body();
        Assertions.assertEquals(OK, executed);
    }
}
