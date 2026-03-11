package io.jpyxie.python.library;

public interface PythonLibraryManager {
    String SHOW = "show";
    String INSTALL = "install";
    String UNINSTALL = "uninstall";
    String UNINSTALL_WITHOUT_CONFIRMATION_OPTION = "--yes";

    boolean exists(PythonLibrary management);

    void install(PythonLibrary management);

    void uninstall(PythonLibrary management);
}
