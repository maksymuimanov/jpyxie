package io.jpyxie.python.library;

import java.util.List;

public class PipPythonLibraryManagerException extends PythonLibraryException {
    public static PipPythonLibraryManagerException failedToCheckExistence(PythonLibrary library, Throwable throwable) {
        return new PipPythonLibraryManagerException("Failed to check existence of library [" + library.getName() + "]", throwable);
    }

    public static PipPythonLibraryManagerException failedToInstall(PythonLibrary library, Throwable throwable) {
        return new PipPythonLibraryManagerException("Failed to install library [" + library.getName() + "]", throwable);
    }

    public static PipPythonLibraryManagerException failedToUninstall(PythonLibrary library, Throwable throwable) {
        return new PipPythonLibraryManagerException("Failed to uninstall library [" + library.getName() + "]", throwable);
    }

    public static PipPythonLibraryManagerException failedPipCommand(List<String> command, Throwable throwable) {
        return new PipPythonLibraryManagerException("Pip command [" + command + "] failed", throwable);
    }

    public static PipPythonLibraryManagerException failedPipCommand(List<String> command, int exitValue) {
        return new PipPythonLibraryManagerException("Pip command [" + command + "] failed with exit code: " + exitValue);
    }

    public static PipPythonLibraryManagerException interrupted(Throwable throwable) {
        return new PipPythonLibraryManagerException("Pip command interrupted", throwable);
    }

    public PipPythonLibraryManagerException() {
        super();
    }

    public PipPythonLibraryManagerException(String message) {
        super(message);
    }

    public PipPythonLibraryManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PipPythonLibraryManagerException(Throwable cause) {
        super(cause);
    }

    public PipPythonLibraryManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
