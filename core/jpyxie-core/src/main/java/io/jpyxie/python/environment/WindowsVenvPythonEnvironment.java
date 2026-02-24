package io.jpyxie.python.environment;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.time.Duration;

@Slf4j
public class WindowsVenvPythonEnvironment extends AbstractVenvPythonEnvironment {
    public static final String SCRIPTS_DIRECTORY = "Scripts";
    public static final String PYTHON_EXE = "python.exe";

    public WindowsVenvPythonEnvironment(OnExistingHandler onExistingHandler) {
        super(onExistingHandler);
    }

    public WindowsVenvPythonEnvironment(String globalPythonExecutable, OnExistingHandler onExistingHandler) {
        super(globalPythonExecutable, onExistingHandler);
    }

    public WindowsVenvPythonEnvironment(String globalPythonExecutable, OnExistingHandler onExistingHandler, String venvParentDirectory) {
        super(globalPythonExecutable, onExistingHandler, venvParentDirectory);
    }

    public WindowsVenvPythonEnvironment(String globalPythonExecutable, String backupPythonExecutable, OnExistingHandler onExistingHandler, String venvParentDirectory) {
        super(globalPythonExecutable, backupPythonExecutable, onExistingHandler, venvParentDirectory);
    }

    public WindowsVenvPythonEnvironment(String globalPythonExecutable, String backupPythonExecutable, OnExistingHandler onExistingHandler, String venvParentDirectory, boolean redirectErrorStream, boolean redirectOutputStream, boolean readOutput, Duration timeout) {
        super(globalPythonExecutable, backupPythonExecutable, onExistingHandler, venvParentDirectory, redirectErrorStream, redirectOutputStream, readOutput, timeout);
    }

    @Override
    protected Path locateSystemPythonExecutablePath() {
        return this.getPath()
                .resolve(SCRIPTS_DIRECTORY)
                .resolve(PYTHON_EXE);
    }
}
