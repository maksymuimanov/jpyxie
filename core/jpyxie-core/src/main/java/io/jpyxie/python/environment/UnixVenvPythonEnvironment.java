package io.jpyxie.python.environment;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.time.Duration;

import static io.jpyxie.python.constant.PythonConstants.PYTHON;

@Slf4j
public class UnixVenvPythonEnvironment extends AbstractVenvPythonEnvironment {
    public static final String BIN_DIRECTORY = "bin";

    public UnixVenvPythonEnvironment(OnExistingHandler onExistingHandler) {
        super(onExistingHandler);
    }

    public UnixVenvPythonEnvironment(String globalPythonExecutable, OnExistingHandler onExistingHandler) {
        super(globalPythonExecutable, onExistingHandler);
    }

    public UnixVenvPythonEnvironment(String globalPythonExecutable, OnExistingHandler onExistingHandler, String venvParentDirectory) {
        super(globalPythonExecutable, onExistingHandler, venvParentDirectory);
    }

    public UnixVenvPythonEnvironment(String globalPythonExecutable, String backupPythonExecutable, OnExistingHandler onExistingHandler, String venvParentDirectory) {
        super(globalPythonExecutable, backupPythonExecutable, onExistingHandler, venvParentDirectory);
    }

    public UnixVenvPythonEnvironment(String globalPythonExecutable, String backupPythonExecutable, OnExistingHandler onExistingHandler, String venvParentDirectory, boolean redirectErrorStream, boolean redirectOutputStream, boolean readOutput, Duration timeout) {
        super(globalPythonExecutable, backupPythonExecutable, onExistingHandler, venvParentDirectory, redirectErrorStream, redirectOutputStream, readOutput, timeout);
    }

    @Override
    protected Path locateSystemPythonExecutablePath() {
        return this.getPath()
                .resolve(BIN_DIRECTORY)
                .resolve(PYTHON);
    }
}
