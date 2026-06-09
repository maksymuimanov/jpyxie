package io.jpyxie.python.environment;

import java.io.IOException;
import java.nio.file.Path;

public class PythonVenvEnvironmentException extends PythonEnvironmentException {
    public static PythonVenvEnvironmentException failedToCreate() {
        return new PythonVenvEnvironmentException("Failed to create venv environment");
    }

    public static PythonVenvEnvironmentException failedToCreate(Throwable throwable) {
        return new PythonVenvEnvironmentException("Failed to create venv environment", throwable);
    }

    public static PythonVenvEnvironmentException interrupted(InterruptedException exception) {
        return new PythonVenvEnvironmentException("Venv interrupted", exception);
    }

    public static PythonVenvEnvironmentException venvNotFound(Path path) {
        return new PythonVenvEnvironmentException("Venv environment does not exist: " + path);
    }

    public static PythonVenvEnvironmentException executableNotFound(Path path) {
        return new PythonVenvEnvironmentException("Python executable not found in " + path);
    }

    public static PythonVenvEnvironmentException failedToLocateExecutable(Throwable throwable) {
        return new PythonVenvEnvironmentException("Python executable not found in ", throwable);
    }

    public static PythonVenvEnvironmentException venvPathCannotBeNull() {
        return new PythonVenvEnvironmentException("venvPath cannot be null");
    }

    public static PythonVenvEnvironmentException failedToDeleteFile(Path path, IOException exception) {
        return new PythonVenvEnvironmentException("Failed to delete file: " + path, exception);
    }

    public static PythonVenvEnvironmentException failedToRemoveVenv(Path path, Throwable throwable) {
        return new PythonVenvEnvironmentException("Failed to delete venv environment: " + path, throwable);
    }

    public static PythonVenvEnvironmentException venvAlreadyExists(PythonEnvironment environment) {
        return new PythonVenvEnvironmentException("Virtual environment already exists at: " + environment);
    }

    public PythonVenvEnvironmentException() {
    }

    public PythonVenvEnvironmentException(String message) {
        super(message);
    }

    public PythonVenvEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonVenvEnvironmentException(Throwable cause) {
        super(cause);
    }

    public PythonVenvEnvironmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
