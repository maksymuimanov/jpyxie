package io.jpyxie.python.environment;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.time.Duration;

@Slf4j
public class WindowsVenvPythonEnvironment extends AbstractVenvPythonEnvironment {
    public static final String SCRIPTS_DIRECTORY = "Scripts";
    public static final String PYTHON_EXE = "python.exe";

    public WindowsVenvPythonEnvironment(ExistingEnvironmentHandler existingEnvironmentHandler) {
        super(existingEnvironmentHandler);
    }

    public WindowsVenvPythonEnvironment(String globalPythonExecutable, ExistingEnvironmentHandler existingEnvironmentHandler) {
        super(globalPythonExecutable, existingEnvironmentHandler);
    }

    public WindowsVenvPythonEnvironment(String globalPythonExecutable, ExistingEnvironmentHandler existingEnvironmentHandler, String venvParentDirectory) {
        super(globalPythonExecutable, existingEnvironmentHandler, venvParentDirectory);
    }

    public WindowsVenvPythonEnvironment(String globalPythonExecutable, String backupPythonExecutable, ExistingEnvironmentHandler existingEnvironmentHandler, String venvParentDirectory) {
        super(globalPythonExecutable, backupPythonExecutable, existingEnvironmentHandler, venvParentDirectory);
    }

    public WindowsVenvPythonEnvironment(String globalPythonExecutable, String backupPythonExecutable, ExistingEnvironmentHandler existingEnvironmentHandler, String venvParentDirectory, boolean redirectErrorStream, boolean redirectOutputStream, boolean readOutput, Duration timeout) {
        super(globalPythonExecutable, backupPythonExecutable, existingEnvironmentHandler, venvParentDirectory, redirectErrorStream, redirectOutputStream, readOutput, timeout);
    }

    @Override
    protected Path locateSystemPythonExecutablePath() {
        return this.getPath()
                .resolve(SCRIPTS_DIRECTORY)
                .resolve(PYTHON_EXE);
    }
}
