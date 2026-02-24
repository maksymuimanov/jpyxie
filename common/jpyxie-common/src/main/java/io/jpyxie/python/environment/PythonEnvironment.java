package io.jpyxie.python.environment;

import java.nio.file.Path;

public interface PythonEnvironment {
    void create();

    String getExecutableOrBackup();

    String getExecutableOrElse(String executable);

    String getExecutable();

    Path getPath();

    void remove();

    boolean exists();

    interface OnExistingHandler {
        boolean handle(PythonEnvironment environment);
    }
}
