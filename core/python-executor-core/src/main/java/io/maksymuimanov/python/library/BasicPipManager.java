package io.maksymuimanov.python.library;

import io.maksymuimanov.python.exception.PythonLibraryManagementException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        this.processCommand(SHOW, management, (exitValue) -> {
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

    private void processCommand(String command, PythonLibraryManagement management) {
        this.processCommand(command, management, (exitValue, commandList) -> {
            if (exitValue != 0) {
                throw new PythonLibraryManagementException(String.format("%s failed with exit code: %d", String.join(" ", commandList), exitValue));
            }
        });
    }

    private void processCommand(String command, PythonLibraryManagement management, Consumer<Integer> exitValueConsumer) {
        this.processCommand(command, management, (exitValue, commandList) -> {
            exitValueConsumer.accept(exitValue);
        });
    }

    private void processCommand(String command, PythonLibraryManagement management, BiConsumer<Integer, List<String>> exitValueCommandsBiConsumer) {
        List<String> commands = new ArrayList<>();
        Collections.addAll(commands, this.pipPath);
        commands.add(command);
        commands.add(management.getName());
        if (management.getOptions() != null) commands.addAll(management.getOptions());
        this.processCommand(commands, exitValueCommandsBiConsumer);
    }

    private void processCommand(List<String> commands) {
        this.processCommand(commands, (exitValue) -> {
            if (exitValue != 0) {
                throw new PythonLibraryManagementException(String.format("%s failed with exit code: %d", String.join(" ", commands), exitValue));
            }
        });
    }

    private void processCommand(List<String> commands, Consumer<Integer> exitValueConsumer) {
        this.processCommand(commands, (exitValue, commandList) -> {
            exitValueConsumer.accept(exitValue);
        });
    }

    private void processCommand(List<String> commands, BiConsumer<Integer, List<String>> exitValueCommandsBiConsumer) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            Process process = processBuilder.command(commands)
                    .redirectErrorStream(true)
                    .start();
            Thread infoThread = new Thread(() -> {
                try (BufferedReader inputReader = process.inputReader()) {
                    inputReader.lines().forEach(log::debug);
                } catch (IOException e) {
                    throw new PythonLibraryManagementException(e);
                }
            });
            infoThread.start();
            int exitValue = process.waitFor();
            infoThread.join();
            exitValueCommandsBiConsumer.accept(exitValue, commands);
        } catch (IOException e) {
            throw new PythonLibraryManagementException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PythonLibraryManagementException(e);
        }
    }
}
