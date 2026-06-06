package io.jpyxie.python.library;

public interface PythonLibraryManager {
    String SHOW_COMMAND = "show";
    String INSTALL_COMMAND = "install";
    String UNINSTALL_COMMAND = "uninstall";
    String UNINSTALL_WITHOUT_CONFIRMATION_OPTION = "--yes";

    boolean exists(PythonLibrary library);

    void install(PythonLibrary library);

    void uninstall(PythonLibrary library);
}
