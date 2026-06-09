package io.jpyxie.python.environment;

import io.jpyxie.python.PythonConstants;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.time.Duration;

@Slf4j
public class UnixVenvPythonEnvironment extends VenvPythonEnvironment {
    public static final String BIN_DIRECTORY = "bin";

    public UnixVenvPythonEnvironment(ExistingEnvironmentHandler existingEnvironmentHandler) {
        super(existingEnvironmentHandler);
    }

    public UnixVenvPythonEnvironment(String globalPythonExecutable, ExistingEnvironmentHandler existingEnvironmentHandler) {
        super(globalPythonExecutable, existingEnvironmentHandler);
    }

    public UnixVenvPythonEnvironment(String globalPythonExecutable, ExistingEnvironmentHandler existingEnvironmentHandler, String venvParentDirectory) {
        super(globalPythonExecutable, existingEnvironmentHandler, venvParentDirectory);
    }

    public UnixVenvPythonEnvironment(String globalPythonExecutable, String backupPythonExecutable, ExistingEnvironmentHandler existingEnvironmentHandler, String venvParentDirectory) {
        super(globalPythonExecutable, backupPythonExecutable, existingEnvironmentHandler, venvParentDirectory);
    }

    public UnixVenvPythonEnvironment(String globalPythonExecutable, String backupPythonExecutable, ExistingEnvironmentHandler existingEnvironmentHandler, String venvParentDirectory, boolean redirectErrorStream, boolean redirectOutputStream, boolean readOutput, Duration timeout) {
        super(globalPythonExecutable, backupPythonExecutable, existingEnvironmentHandler, venvParentDirectory, redirectErrorStream, redirectOutputStream, readOutput, timeout);
    }

    @Override
    protected Path locateSystemExecutablePath() {
        return this.getPath()
                .resolve(BIN_DIRECTORY)
                .resolve(PythonConstants.PYTHON);
    }
}
