package io.jpyxie.python.library;

public class GrpcPythonLibraryManagerException extends PythonLibraryException {
    public static GrpcPythonLibraryManagerException failedToInstall(PythonLibrary library) {
        return new GrpcPythonLibraryManagerException("Failed to install library [" + library.getName() + "]", throwable);
    }

    public static GrpcPythonLibraryManagerException failedToUninstall(PythonLibrary library) {
        return new GrpcPythonLibraryManagerException("Failed to uninstall library [" + library.getName() + "]", throwable);
    }


    public static GrpcPythonLibraryManagerException failedToExecutePipCommand(String command, PythonLibrary library, Throwable throwable) {
        return new GrpcPythonLibraryManagerException("Failed to execute pip command [" + command + "] for [" + library.getName() + "]", throwable);
    }

    public GrpcPythonLibraryManagerException() {
        super();
    }

    public GrpcPythonLibraryManagerException(String message) {
        super(message);
    }

    public GrpcPythonLibraryManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrpcPythonLibraryManagerException(Throwable cause) {
        super(cause);
    }

    public GrpcPythonLibraryManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
