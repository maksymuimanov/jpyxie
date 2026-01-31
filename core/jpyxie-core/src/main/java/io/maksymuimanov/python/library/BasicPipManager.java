package io.maksymuimanov.python.library;

import io.maksymuimanov.python.exception.PythonLibraryManagementException;
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
public class BasicPipManager implements PipManager {
    public static final String DEFAULT_COMMAND = "python -m pip";
    public static final boolean DEFAULT_REDIRECT_ERROR_STREAM = false;
    public static final boolean DEFAULT_REDIRECT_OUTPUT_STREAM = false;
    public static final boolean DEFAULT_READ_OUTPUT = false;
    public static final Duration DEFAULT_TIMEOUT = Duration.ofMillis(-1);
    public static final String SHOW = "show";
    public static final String EXCEPTION_MESSAGE_FORMAT = "%s failed with exit code: %d";
    private final String[] pipPath;
    private final boolean redirectErrorStream;
    private final boolean redirectOutputStream;
    private final boolean readOutput;
    private final Duration timeout;

    public BasicPipManager() {
        this(DEFAULT_COMMAND, DEFAULT_REDIRECT_ERROR_STREAM, DEFAULT_REDIRECT_OUTPUT_STREAM, DEFAULT_READ_OUTPUT, DEFAULT_TIMEOUT);
    }

    public BasicPipManager(String pipPath) {
        this(pipPath, DEFAULT_REDIRECT_ERROR_STREAM, DEFAULT_REDIRECT_OUTPUT_STREAM, DEFAULT_READ_OUTPUT, DEFAULT_TIMEOUT);
    }

    public BasicPipManager(String pipPath, boolean redirectErrorStream, boolean redirectOutputStream, boolean readOutput, Duration timeout) {
        this.pipPath = pipPath.split(" ");
        this.redirectErrorStream = redirectErrorStream;
        this.redirectOutputStream = redirectOutputStream;
        this.readOutput = readOutput;
        this.timeout = timeout;
    }

    @Override
    public boolean exists(PythonLibraryManagement management) {
        log.debug("Checking if library [{}] exists", management.getName());
        AtomicBoolean exists = new AtomicBoolean(false);
        this.processCommand(SHOW, management.getName(), exitValue -> {
            if (exitValue == 0) {
                exists.set(true);
                log.debug("Library [{}] exists", management.getName());
            } else {
                log.debug("Library [{}] does not exist (exit code: [{}])", management.getName(), exitValue);
            }
        });
        return exists.get();
    }

    @Override
    public void install(PythonLibraryManagement management) {
        log.info("Installing Python library [{}] with options [{}]", management.getName(), management.getOptions());
        this.processCommand(INSTALL, management);
    }

    @Override
    public void uninstall(PythonLibraryManagement management) {
        log.info("Uninstalling Python library [{}] with options [{}]", management.getName(), management.getOptions());
        management.addOption(UNINSTALL_WITHOUT_CONFIRMATION_OPTION);
        this.processCommand(UNINSTALL, management);
    }

    protected void processCommand(String command, String name) {
        this.processCommand(command, name, (exitValue, commandList) -> {
            if (exitValue != 0) {
                throw new PythonLibraryManagementException(String.format(EXCEPTION_MESSAGE_FORMAT, String.join(" ", commandList), exitValue));
            }
        });
    }

    protected void processCommand(String command, PythonLibraryManagement management) {
        this.processCommand(command, management, (exitValue, commandList) -> {
            if (exitValue != 0) {
                throw new PythonLibraryManagementException(String.format(EXCEPTION_MESSAGE_FORMAT, String.join(" ", commandList), exitValue));
            }
        });
    }

    protected void processCommand(String command, PythonLibraryManagement management, IntConsumer exitValueConsumer) {
        this.processCommand(command, management, (exitValue, commandList) -> {
            exitValueConsumer.accept(exitValue);
        });
    }

    protected void processCommand(String command, String name, IntConsumer exitValueConsumer) {
        this.processCommand(command, name, (exitValue, commandList) -> {
            exitValueConsumer.accept(exitValue);
        });
    }

    protected void processCommand(String command, PythonLibraryManagement management, BiConsumer<Integer, List<String>> exitValueCommandsBiConsumer) {
        List<String> commands = new ArrayList<>();
        Collections.addAll(commands, this.pipPath);
        commands.add(command);
        commands.add(management.getName());
        if (management.getOptions() != null) commands.addAll(management.getOptions());
        this.processCommand(commands, exitValueCommandsBiConsumer);
    }

    protected void processCommand(String command, String name, BiConsumer<Integer, List<String>> exitValueCommandsBiConsumer) {
        List<String> commands = new ArrayList<>();
        Collections.addAll(commands, this.pipPath);
        commands.add(command);
        commands.add(name);
        this.processCommand(commands, exitValueCommandsBiConsumer);
    }

    protected void processCommand(List<String> commands) {
        this.processCommand(commands, exitValue -> {
            if (exitValue != 0) {
                throw new PythonLibraryManagementException(String.format(EXCEPTION_MESSAGE_FORMAT, String.join(" ", commands), exitValue));
            }
        });
    }

    protected void processCommand(List<String> commands, IntConsumer exitValueConsumer) {
        this.processCommand(commands, (exitValue, commandList) -> {
            exitValueConsumer.accept(exitValue);
        });
    }

    protected void processCommand(List<String> commands, BiConsumer<Integer, List<String>> exitValueCommandsBiConsumer) {
        String combinedCommands = String.join(" ", commands);
        try {
            log.debug("Executing pip command: [{}]", combinedCommands);
            int exitValue = new ProcessExecutor()
                    .command(commands)
                    .redirectErrorStream(this.redirectErrorStream)
                    .redirectOutput(this.redirectOutputStream ? Slf4jStream.of(log).asDebug() : NullOutputStream.NULL_OUTPUT_STREAM)
                    .readOutput(this.readOutput)
                    .timeout(this.timeout.toMillis(), TimeUnit.MILLISECONDS)
                    .execute()
                    .getExitValue();
            log.debug("Pip command completed with exit code: [{}]", exitValue);
            exitValueCommandsBiConsumer.accept(exitValue, commands);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Pip command interrupted: [{}]", combinedCommands, e);
            throw new PythonLibraryManagementException(e);
        } catch (Exception e) {
            log.error("Pip command failed: [{}]", combinedCommands, e);
            throw new PythonLibraryManagementException(e);
        }
    }
}
