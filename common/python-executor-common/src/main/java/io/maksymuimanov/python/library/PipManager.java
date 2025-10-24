package io.maksymuimanov.python.library;

public interface PipManager {
    String INSTALL = "install";
    String UNINSTALL = "uninstall";
    String SHOW = "show";

    boolean exists(PythonLibraryManagement management);

    void install(PythonLibraryManagement management);

    void uninstall(PythonLibraryManagement management);
}
