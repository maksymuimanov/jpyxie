package io.maksymuimanov.python.output;

import io.maksymuimanov.python.error.BasicPythonErrorProcessHandler;
import io.maksymuimanov.python.exception.PythonProcessReadingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.maksymuimanov.python.constant.TestConstants.*;

class BasicPythonErrorProcessHandlerTests {
    private static final ProcessHandler<Void> ERROR_PROCESS_HANDLER = new BasicPythonErrorProcessHandler();

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {BAD_SCRIPT_0, BAD_SCRIPT_1, BAD_SCRIPT_2, BAD_SCRIPT_3})
    void testHandle(String script) {
        Process process = new ProcessBuilder("python3", "-c", script).start();
        process.waitFor();
        Assertions.assertThrows(PythonProcessReadingException.class, () -> ERROR_PROCESS_HANDLER.handle(process));
        Assertions.assertNotEquals(0, process.exitValue());
    }
}
