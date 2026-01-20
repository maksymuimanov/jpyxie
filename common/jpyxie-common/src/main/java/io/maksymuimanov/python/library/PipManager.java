package io.maksymuimanov.python.library;

public interface PipManager {
    String SHOW = "show";
    String INSTALL = "install";
    String UNINSTALL = "uninstall";

    boolean exists(PythonLibraryManagement management);

    void install(PythonLibraryManagement management);

    void uninstall(PythonLibraryManagement management);
}
