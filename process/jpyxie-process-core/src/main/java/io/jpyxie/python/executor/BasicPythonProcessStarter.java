package io.jpyxie.python.executor;

import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.exception.PythonProcessStartException;
import io.jpyxie.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BasicPythonProcessStarter implements ProcessStarter {
    private static final String COMMAND_HEADER = "-c";
    private final PythonEnvironment pythonEnvironment;

    @Override
    public Process start(PythonScript script) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String scriptBody = script.toString();
            String pythonExecutable = this.pythonEnvironment.getExecutableOrBackup();
            processBuilder.command(pythonExecutable, COMMAND_HEADER, scriptBody.replace("\"", "\"\""));
            log.info("Python script is going to be executed");
            Process process = processBuilder.start();
            process.waitFor();
            return process;
        } catch (Exception e) {
            throw new PythonProcessStartException(e);
        }
    }
}