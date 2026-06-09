package io.jpyxie.python.library;

public class SubprocessPythonLibraryManagerException extends PythonLibraryException {
    public static SubprocessPythonLibraryManagerException failedToCheckExistence(PythonLibrary library, Throwable throwable) {
        return new SubprocessPythonLibraryManagerException("Failed to check existence of library [" + library.getName() + "]", throwable);
    }

    public static SubprocessPythonLibraryManagerException failedToInstall(PythonLibrary library, Throwable throwable) {
        return new SubprocessPythonLibraryManagerException("Failed to install library [" + library.getName() + "]", throwable);
    }

    public static SubprocessPythonLibraryManagerException failedToUninstall(PythonLibrary library, Throwable throwable) {
        return new SubprocessPythonLibraryManagerException("Failed to uninstall library [" + library.getName() + "]", throwable);
    }

    public SubprocessPythonLibraryManagerException() {
        super();
    }

    public SubprocessPythonLibraryManagerException(String message) {
        super(message);
    }

    public SubprocessPythonLibraryManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubprocessPythonLibraryManagerException(Throwable cause) {
        super(cause);
    }

    public SubprocessPythonLibraryManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
