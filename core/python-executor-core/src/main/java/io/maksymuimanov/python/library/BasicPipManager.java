package io.maksymuimanov.python.library;

import io.maksymuimanov.python.exception.PythonLibraryManagementException;
import lombok.extern.slf4j.Slf4j;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
public class BasicPipManager implements PipManager {
    public static final String UNINSTALL_WITHOUT_CONFIRMATION_OPTION = "--yes";
    private final String[] pipPath;

    public BasicPipManager(String pipPath) {
        this.pipPath = pipPath.split(" ");
    }

    @Override
    public boolean exists(PythonLibraryManagement management) {
        AtomicBoolean exists = new AtomicBoolean(false);
        this.processCommand(SHOW, management.getName(), (exitValue) -> {
            if (exitValue == 0) {
                exists.set(true);
            }
        });
        return exists.get();
    }

    @Override
    public void install(PythonLibraryManagement management) {
        this.processCommand(INSTALL, management);
    }

    @Override
    public void uninstall(PythonLibraryManagement management) {
        management.addOption(UNINSTALL_WITHOUT_CONFIRMATION_OPTION);
        this.processCommand(UNINSTALL, management);
    }

    protected void processCommand(String command, String name) {
        this.processCommand(command, name, (exitValue, commandList) -> {
            if (exitValue != 0) {
                throw new PythonLibraryManagementException(String.format("%s failed with exit code: %d", String.join(" ", commandList), exitValue));
            }
        });
    }

    protected void processCommand(String command, PythonLibraryManagement management) {
        this.processCommand(command, management, (exitValue, commandList) -> {
            if (exitValue != 0) {
                throw new PythonLibraryManagementException(String.format("%s failed with exit code: %d", String.join(" ", commandList), exitValue));
            }
        });
    }

    protected void processCommand(String command, PythonLibraryManagement management, Consumer<Integer> exitValueConsumer) {
        this.processCommand(command, management, (exitValue, commandList) -> {
            exitValueConsumer.accept(exitValue);
        });
    }

    protected void processCommand(String command, String name, Consumer<Integer> exitValueConsumer) {
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
        this.processCommand(commands, (exitValue) -> {
            if (exitValue != 0) {
                throw new PythonLibraryManagementException(String.format("%s failed with exit code: %d", String.join(" ", commands), exitValue));
            }
        });
    }

    protected void processCommand(List<String> commands, Consumer<Integer> exitValueConsumer) {
        this.processCommand(commands, (exitValue, commandList) -> {
            exitValueConsumer.accept(exitValue);
        });
    }

    protected void processCommand(List<String> commands, BiConsumer<Integer, List<String>> exitValueCommandsBiConsumer) {
        try {
            int exitValue = new ProcessExecutor()
                    .command(commands)
                    .redirectErrorStream(true)
                    .readOutput(true)
                    .timeout(5, TimeUnit.MINUTES)
                    .redirectOutput(Slf4jStream.of(log).asDebug())
                    .execute()
                    .getExitValue();
            exitValueCommandsBiConsumer.accept(exitValue, commands);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PythonLibraryManagementException(e);
        } catch (Exception e) {
            throw new PythonLibraryManagementException(e);
        }
    }
}
