package io.jpyxie.python.library;

import io.jpyxie.python.PythonConstants;
import io.jpyxie.python.environment.PythonEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.NullOutputStream;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

@Slf4j
public class PipPythonLibraryManager implements PythonLibraryManager {
    public static final String DEFAULT_COMMAND = "pip";
    public static final boolean DEFAULT_REDIRECT_ERROR_STREAM = true;
    public static final boolean DEFAULT_REDIRECT_OUTPUT_STREAM = true;
    public static final boolean DEFAULT_READ_OUTPUT = true;
    public static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(5);
    private final PythonEnvironment environment;
    private final String[] pipCommand;
    private final boolean redirectErrorStream;
    private final boolean redirectOutputStream;
    private final boolean readOutput;
    private final Duration timeout;

    public PipPythonLibraryManager(PythonEnvironment environment) {
        this(environment, DEFAULT_COMMAND);
    }

    public PipPythonLibraryManager(PythonEnvironment environment,
                                   String pipCommand) {
        this(environment, pipCommand, DEFAULT_TIMEOUT);
    }

    public PipPythonLibraryManager(PythonEnvironment environment,
                                   String pipCommand,
                                   Duration timeout) {
        this(environment, pipCommand, DEFAULT_REDIRECT_ERROR_STREAM, DEFAULT_REDIRECT_OUTPUT_STREAM, DEFAULT_READ_OUTPUT, timeout);
    }

    public PipPythonLibraryManager(PythonEnvironment environment,
                                   String pipCommand,
                                   boolean redirectErrorStream,
                                   boolean redirectOutputStream,
                                   boolean readOutput,
                                   Duration timeout) {
        this.environment = environment;
        this.pipCommand = new String[]{PythonConstants.M, pipCommand};
        this.redirectErrorStream = redirectErrorStream;
        this.redirectOutputStream = redirectOutputStream;
        this.readOutput = readOutput;
        this.timeout = timeout;
    }

    @Override
    public boolean exists(PythonLibrary library) {
        try {
            log.debug("Checking if library [{}] exists", library.getName());
            AtomicBoolean exists = new AtomicBoolean(false);
            this.processCommand(SHOW_COMMAND, library.getName(), exitValue -> {
                if (exitValue == 0) {
                    exists.set(true);
                    log.debug("Library [{}] exists", library.getName());
                } else {
                    log.debug("Library [{}] does not exist (exit code: [{}])", library.getName(), exitValue);
                }
            });
            return exists.get();
        } catch (Exception e) {
            throw PipPythonLibraryManagerException.failedToCheckExistence(library, e);
        }
    }

    @Override
    public void install(PythonLibrary library) {
        try {
            log.info("Installing Python library [{}] with options [{}]", library.getName(), library.getOptions());
            this.processCommand(INSTALL_COMMAND, library);
        } catch (Exception e) {
            throw PipPythonLibraryManagerException.failedToInstall(library, e);
        }
    }

    @Override
    public void uninstall(PythonLibrary library) {
        try {
            log.info("Uninstalling Python library [{}] with options [{}]", library.getName(), library.getOptions());
            library.addOption(UNINSTALL_WITHOUT_CONFIRMATION_OPTION);
            this.processCommand(UNINSTALL_COMMAND, library);
        } catch (Exception e) {
            throw PipPythonLibraryManagerException.failedToUninstall(library, e);
        }
    }

    protected void processCommand(String command, PythonLibrary library) {
        this.processCommand(command, library, (exitValue, commandList) -> {
            if (exitValue != 0) {
                throw PipPythonLibraryManagerException.failedPipCommand(commandList, exitValue);
            }
        });
    }

    protected void processCommand(String command, String name, IntConsumer exitValueConsumer) {
        this.processCommand(command, name, (exitValue, commandList) ->
                exitValueConsumer.accept(exitValue));
    }

    protected void processCommand(String command, PythonLibrary library, BiConsumer<Integer, List<String>> exitValueCommandsBiConsumer) {
        List<String> commands = new ArrayList<>();
        String pythonExecutable = this.environment.getExecutableOrBackup();
        commands.add(pythonExecutable);
        Collections.addAll(commands, this.pipCommand);
        commands.add(command);
        commands.add(library.getName());
        if (library.getOptions() != null) commands.addAll(library.getOptions());
        this.processCommand(commands, exitValueCommandsBiConsumer);
    }

    protected void processCommand(String command, String name, BiConsumer<Integer, List<String>> exitValueCommandsBiConsumer) {
        List<String> commands = new ArrayList<>();
        String pythonExecutable = this.environment.getExecutableOrBackup();
        commands.add(pythonExecutable);
        Collections.addAll(commands, this.pipCommand);
        commands.add(command);
        commands.add(name);
        this.processCommand(commands, exitValueCommandsBiConsumer);
    }

    protected void processCommand(List<String> commands, BiConsumer<Integer, List<String>> exitValueCommandsBiConsumer) {
        try {
            log.debug("Executing pip command: [{}]", commands);
            int exitValue = new ProcessExecutor()
                    .command(commands)
                    .redirectErrorStream(this.redirectErrorStream)
                    .redirectOutput(this.redirectOutputStream
                            ? Slf4jStream.of(log).asDebug()
                            : NullOutputStream.NULL_OUTPUT_STREAM)
                    .readOutput(this.readOutput)
                    .timeout(this.timeout.toMillis(), TimeUnit.MILLISECONDS)
                    .execute()
                    .getExitValue();
            log.debug("Pip command completed with exit code: [{}]", exitValue);
            exitValueCommandsBiConsumer.accept(exitValue, commands);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw PipPythonLibraryManagerException.interrupted(e);
        } catch (Exception e) {
            throw PipPythonLibraryManagerException.failedPipCommand(commands, e);
        }
    }
}