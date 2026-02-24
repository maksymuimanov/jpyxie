package io.jpyxie.python.environment;

import io.jpyxie.python.exception.PythonEnvironmentException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.NullOutputStream;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static io.jpyxie.python.constant.PythonConstants.PYTHON;

@Slf4j
public class VenvPythonEnvironment implements PythonEnvironment {
    private static final String PROJECT_DIR_PROPERTY = "user.dir";
    private static final String CURRENT_DIRECTORY = ".";
    public static final String SCRIPTS_DIRECTORY = "Scripts";
    public static final String PYTHON_EXE = "python.exe";
    public static final String BIN_DIRECTORY = "bin";
    public static final String DEFAULT_VENV_PARENT_DIRECTORY = "venv";
    public static final boolean DEFAULT_REDIRECT_ERROR_STREAM = true;
    public static final boolean DEFAULT_REDIRECT_OUTPUT_STREAM = true;
    public static final boolean DEFAULT_READ_OUTPUT = true;
    public static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(1);
    private final String globalPythonExecutable;
    private final String backupPythonExecutable;
    private final OnExistingHandler onExistingHandler;
    private final String venvParentDirectory;
    private final boolean redirectErrorStream;
    private final boolean redirectOutputStream;
    private final boolean readOutput;
    private final Duration timeout;
    @Nullable
    private Path venvPath;
    @Nullable
    private String executable;

    public VenvPythonEnvironment(OnExistingHandler onExistingHandler) {
        this(PYTHON, onExistingHandler);
    }

    public VenvPythonEnvironment(String globalPythonExecutable,
                                 OnExistingHandler onExistingHandler) {
        this(globalPythonExecutable, onExistingHandler, DEFAULT_VENV_PARENT_DIRECTORY);
    }

    public VenvPythonEnvironment(String globalPythonExecutable,
                                 OnExistingHandler onExistingHandler,
                                 String venvParentDirectory) {
        this(globalPythonExecutable, globalPythonExecutable, onExistingHandler, venvParentDirectory);
    }

    public VenvPythonEnvironment(String globalPythonExecutable,
                                 String backupPythonExecutable,
                                 OnExistingHandler onExistingHandler,
                                 String venvParentDirectory) {
        this(globalPythonExecutable, backupPythonExecutable, onExistingHandler, venvParentDirectory, DEFAULT_REDIRECT_ERROR_STREAM, DEFAULT_REDIRECT_OUTPUT_STREAM, DEFAULT_READ_OUTPUT, DEFAULT_TIMEOUT);
    }

    public VenvPythonEnvironment(String globalPythonExecutable,
                                 String backupPythonExecutable,
                                 OnExistingHandler onExistingHandler,
                                 String venvParentDirectory,
                                 boolean redirectErrorStream,
                                 boolean redirectOutputStream,
                                 boolean readOutput,
                                 Duration timeout) {
        this.globalPythonExecutable = globalPythonExecutable;
        this.backupPythonExecutable = backupPythonExecutable;
        this.onExistingHandler = onExistingHandler;
        this.venvParentDirectory = venvParentDirectory;
        this.redirectErrorStream = redirectErrorStream;
        this.redirectOutputStream = redirectOutputStream;
        this.readOutput = readOutput;
        this.timeout = timeout;
    }

    @Override
    public synchronized void create() {
        try {
            log.debug("Creating venv environment");
            this.executable = null;
            if (this.exists()
                    && this.onExistingHandler.handle(this)) {
                log.info("Venv environment already exists: {}", this.getPath());
                return;
            }
            this.initializeVenvPath();
            log.debug("Venv environment path: {}", this.getPath());
            log.info("Creating new venv environment: {}", this.getPath());
            int exitValue = new ProcessExecutor()
                    .command(this.globalPythonExecutable, "-m", "venv", String.valueOf(this.getPath()))
                    .redirectErrorStream(this.redirectErrorStream)
                    .redirectOutput(this.redirectOutputStream
                            ? Slf4jStream.of(log).asDebug()
                            : NullOutputStream.NULL_OUTPUT_STREAM)
                    .readOutput(this.readOutput)
                    .timeout(this.timeout.toMillis(), TimeUnit.MILLISECONDS)
                    .execute()
                    .getExitValue();
            if (exitValue != 0) throw new PythonEnvironmentException("Venv creation failed");
            log.info("Venv creation completed with exit code: [{}]", exitValue);
        } catch (IOException | TimeoutException e) {
            log.error("Failed to create venv environment", e);
            throw new PythonEnvironmentException(e);
        } catch (InterruptedException e) {
            log.error("Venv creation interrupted", e);
            Thread.currentThread().interrupt();
            throw new PythonEnvironmentException(e);
        }
    }

    private void initializeVenvPath() {
        if (this.venvPath == null) {
            this.venvPath = this.getProjectDirectory()
                    .resolve(this.venvParentDirectory)
                    .normalize();
        }
    }

    private Path getProjectDirectory() {
        String userDirectory = System.getProperty(PROJECT_DIR_PROPERTY);
        Path rootDirectory = Path.of(CURRENT_DIRECTORY)
                .normalize()
                .toAbsolutePath();
        if (rootDirectory.startsWith(userDirectory) ) {
            return rootDirectory;
        } else {
            throw new PythonEnvironmentException("Project directory is not a child of user directory: " + userDirectory);
        }
    }


    @Override
    public String getExecutableOrBackup() {
        return this.getExecutableOrElse(this.backupPythonExecutable);
    }

    @Override
    public String getExecutableOrElse(String executable) {
        return this.exists()
                ? this.getExecutable()
                : executable;
    }

    @Override
    public String getExecutable() {
        if (!this.exists())
            throw new PythonEnvironmentException("Venv environment does not exist: " + this.getPath());
        if (this.executable != null)
            return this.executable;
        return this.locatePythonExecutable();
    }

    private String locatePythonExecutable() {
        this.locateLinuxPythonExecutable();
        if (this.executable != null)
            return this.executable;
        this.locateWindowsPythonExecutable();
        if (this.executable != null)
            return this.executable;
        throw new PythonEnvironmentException("Python executable not found in venv: " + this.getPath());
    }

    private void locateLinuxPythonExecutable() {
        Path unix = this.getPath()
                .resolve(BIN_DIRECTORY)
                .resolve(PYTHON);
        if (Files.exists(unix))
            this.executable = unix.toString();
    }

    private void locateWindowsPythonExecutable() {
        Path win = this.getPath()
                .resolve(SCRIPTS_DIRECTORY)
                .resolve(PYTHON_EXE);
        if (Files.exists(win))
            this.executable = win.toString();
    }

    @Override
    public Path getPath() {
        if (this.venvPath == null)
            throw new PythonEnvironmentException("venvPath cannot be null");
        return this.venvPath;
    }

    @Override
    public void remove() {
        try {
            if (!this.exists())
                throw new PythonEnvironmentException("Venv environment does not exist: " + this.getPath());
            try (Stream<Path> directoryStream = Files.walk(this.getPath())) {
                directoryStream.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                log.error("Failed to delete file: {}", path, e);
                                throw new PythonEnvironmentException(e);
                            }
                        });
            }
        } catch (Exception e) {
            log.error("Failed to delete venv environment: {}", this.getPath(), e);
            throw new PythonEnvironmentException(e);
        } finally {
            this.executable = null;
            this.venvPath = null;
        }
    }

    @Override
    public boolean exists() {
        try {
            return this.venvPath != null
                    && Files.exists(this.getPath());
        } catch (Exception e) {
            throw new PythonEnvironmentException(e);
        }
    }

    public static class SkipExistingHandler implements OnExistingHandler {
        @Override
        public boolean handle(PythonEnvironment environment) {
            log.info("Skipping creation of venv environment: {}", environment);
            return true;
        }
    }

    public static class RemoveExistingHandler implements OnExistingHandler {
        @Override
        public boolean handle(PythonEnvironment environment) {
            environment.remove();
            return false;
        }
    }

    public static class FailExistingHandler implements OnExistingHandler {
        @Override
        public boolean handle(PythonEnvironment environment) {
            throw new PythonEnvironmentException("Virtual environment already exists at: " + environment);
        }
    }
}
