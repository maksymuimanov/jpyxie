package io.jpyxie.python.environment;

import io.jpyxie.python.PythonConstants;
import io.jpyxie.python.common.FileUtils;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@Slf4j
public abstract class VenvPythonEnvironment implements PythonEnvironment {
    public static final String VENV = "venv";
    public static final boolean DEFAULT_REDIRECT_ERROR_STREAM = true;
    public static final boolean DEFAULT_REDIRECT_OUTPUT_STREAM = true;
    public static final boolean DEFAULT_READ_OUTPUT = true;
    public static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(3);
    private final String globalExecutable;
    private final String backupExecutable;
    private final ExistingEnvironmentHandler existingEnvironmentHandler;
    private final String venvParentDirectory;
    private final boolean redirectErrorStream;
    private final boolean redirectOutputStream;
    private final boolean readOutput;
    private final Duration timeout;
    private final AtomicBoolean created;
    @Nullable
    private Path path;
    @Nullable
    private String executable;

    protected VenvPythonEnvironment(ExistingEnvironmentHandler existingEnvironmentHandler) {
        this(PythonConstants.PYTHON, existingEnvironmentHandler);
    }

    protected VenvPythonEnvironment(String globalExecutable,
                                    ExistingEnvironmentHandler existingEnvironmentHandler) {
        this(globalExecutable, existingEnvironmentHandler, VENV);
    }

    protected VenvPythonEnvironment(String globalExecutable,
                                    ExistingEnvironmentHandler existingEnvironmentHandler,
                                    String venvParentDirectory) {
        this(globalExecutable, globalExecutable, existingEnvironmentHandler, venvParentDirectory);
    }

    protected VenvPythonEnvironment(String globalExecutable,
                                    String backupExecutable,
                                    ExistingEnvironmentHandler existingEnvironmentHandler,
                                    String venvParentDirectory) {
        this(globalExecutable, backupExecutable, existingEnvironmentHandler, venvParentDirectory, DEFAULT_REDIRECT_ERROR_STREAM, DEFAULT_REDIRECT_OUTPUT_STREAM, DEFAULT_READ_OUTPUT, DEFAULT_TIMEOUT);
    }

    protected VenvPythonEnvironment(String globalExecutable,
                                    String backupExecutable,
                                    ExistingEnvironmentHandler existingEnvironmentHandler,
                                    String venvParentDirectory,
                                    boolean redirectErrorStream,
                                    boolean redirectOutputStream,
                                    boolean readOutput,
                                    Duration timeout) {
        this.globalExecutable = globalExecutable;
        this.backupExecutable = backupExecutable;
        this.existingEnvironmentHandler = existingEnvironmentHandler;
        this.venvParentDirectory = venvParentDirectory;
        this.redirectErrorStream = redirectErrorStream;
        this.redirectOutputStream = redirectOutputStream;
        this.readOutput = readOutput;
        this.timeout = timeout;
        this.created = new AtomicBoolean();
    }

    @Override
    public void create() {
        try {
            log.debug("Creating venv environment");
            this.created.set(true);
            if (this.exists()) {
                log.info("Venv environment already exists: {}", this.getPath());
                this.existingEnvironmentHandler.handle(this);
                return;
            }
            log.info("Initializing venv path");
            this.initializePath();
            log.info("Creating new venv environment: {}", this.getPath());
            int exitValue = this.executeCreationCommand();
            if (exitValue != 0) throw PythonVenvEnvironmentException.failedToCreate();
            log.info("Initializing venv executable");
            this.initializeExecutable();
            log.info("Venv creation completed with exit code: [{}]", exitValue);
        } catch (IOException | TimeoutException e) {
            this.created.set(false);
            throw PythonVenvEnvironmentException.failedToCreate(e);
        } catch (InterruptedException e) {
            this.created.set(false);
            Thread.currentThread().interrupt();
            throw PythonVenvEnvironmentException.interrupted(e);
        }
    }

    private void initializePath() {
        this.path = FileUtils.getProjectDirectory()
                .resolve(this.venvParentDirectory)
                .normalize();
    }

    private int executeCreationCommand() throws IOException, InterruptedException, TimeoutException {
        return new ProcessExecutor()
                .command(this.globalExecutable, "-m", VENV, String.valueOf(this.getPath()))
                .redirectErrorStream(this.redirectErrorStream)
                .redirectOutput(this.redirectOutputStream
                        ? Slf4jStream.of(log).asDebug()
                        : NullOutputStream.NULL_OUTPUT_STREAM)
                .readOutput(this.readOutput)
                .timeout(this.timeout.toMillis(), TimeUnit.MILLISECONDS)
                .execute()
                .getExitValue();
    }

    private void initializeExecutable() {
        try {
            Path systemExecutablePath = this.locateSystemExecutablePath();
            if (Files.exists(systemExecutablePath)) {
                this.executable = systemExecutablePath.toString();
            } else {
                throw PythonVenvEnvironmentException.executableNotFound(systemExecutablePath);
            }
        } catch (Exception e) {
            throw PythonVenvEnvironmentException.failedToLocateExecutable(e);
        }
    }

    protected abstract Path locateSystemExecutablePath();

    @Override
    public String getExecutableOrBackup() {
        return this.getExecutableOrElse(this.backupExecutable);
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
            throw PythonVenvEnvironmentException.venvNotFound(this.getPath());
        return this.executable;
    }

    @Override
    public Path getPathOrElse(Path path) {
        return this.exists()
                ? this.getPath()
                : path;
    }

    @Override
    public Path getPath() {
        return Optional.ofNullable(this.path)
                .orElseThrow(PythonVenvEnvironmentException::venvPathCannotBeNull);
    }

    @Override
    public void remove() {
        try {
            if (!this.exists())
                throw PythonVenvEnvironmentException.venvNotFound(this.getPath());
            try (Stream<Path> directoryStream = Files.walk(this.getPath())) {
                directoryStream.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                throw PythonVenvEnvironmentException.failedToDeleteFile(path, e);
                            }
                        });
            }
        } catch (Exception e) {
            throw PythonVenvEnvironmentException.failedToRemoveVenv(this.getPath(), e);
        } finally {
            this.created.set(false);
            this.executable = null;
            this.path = null;
        }
    }

    @Override
    public boolean exists() {
        return this.created.get()
                && this.path != null
                && this.executable != null
                && Files.exists(this.getPath());
    }

    public static class SkipExistingEnvironmentHandler implements ExistingEnvironmentHandler {
        @Override
        public void handle(PythonEnvironment environment) {
            log.info("Skipping creation of venv environment: {}", environment);
        }
    }

    public static class RemoveExistingEnvironmentHandler implements ExistingEnvironmentHandler {
        @Override
        public void handle(PythonEnvironment environment) {
            environment.remove();
            environment.create();
        }
    }

    public static class FailExistingEnvironmentHandler implements ExistingEnvironmentHandler {
        @Override
        public void handle(PythonEnvironment environment) {
            throw PythonVenvEnvironmentException.venvAlreadyExists(environment);
        }
    }
}
