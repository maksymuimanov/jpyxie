package io.maksymuimanov.python.output;

import io.maksymuimanov.python.executor.BasicPythonOutputProcessHandler;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import static io.maksymuimanov.python.constant.TestConstants.SIMPLE_SCRIPT_3;

class BasicPythonOutputProcessHandlerTests {
    private static final ProcessHandler<String> INPUT_PROCESS_HANDLER = new BasicPythonOutputProcessHandler("r4java", true);

    @SneakyThrows
    @Test
    void testHandle() {
        Process process = new ProcessBuilder("python3", "-c", SIMPLE_SCRIPT_3).start();
        process.waitFor();
        Assumptions.assumeTrue(process.exitValue() == 0);

        String result = INPUT_PROCESS_HANDLER.handle(process);
        Assertions.assertEquals("4", result);
    }
}
