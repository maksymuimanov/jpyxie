package io.maksymuimanov.python.library;

import org.jspecify.annotations.Nullable;

import java.util.List;

@SuppressWarnings({"LombokGetterMayBeUsed", "LombokSetterMayBeUsed"})
public class JepLibraryManagement extends PythonLibraryManagement {
    public static final String JEP_LIBRARY_NAME = "jep";
    public static final String NO_CACHE_DIR_OPTION = "--no-cache-dir";
    public static final String NO_BUILD_ISOLATION_OPTION = "--no-build-isolation";
    private boolean noCacheDir = true;
    private boolean noBuildIsolation = true;
    @Nullable
    private String pythonExecutablePath;
    @Nullable
    private String jepExecutablePath;

    public JepLibraryManagement() {
        this(JEP_LIBRARY_NAME);
    }

    public JepLibraryManagement(List<String> options) {
        this(JEP_LIBRARY_NAME, options);
    }

    public JepLibraryManagement(String name) {
        super(name);
    }

    public JepLibraryManagement(String name, List<String> options) {
        super(name, options);
    }

    @Override
    @Nullable
    public List<String> getOptions() {
        List<String> options = super.getOptions();
        if (this.isNoCacheDir()) this.addOption(NO_CACHE_DIR_OPTION);
        if (this.isNoBuildIsolation()) this.addOption(NO_BUILD_ISOLATION_OPTION);
        return options;
    }

    public boolean isNoCacheDir() {
        return noCacheDir;
    }

    public void setNoCacheDir(boolean noCacheDir) {
        this.noCacheDir = noCacheDir;
    }

    public boolean isNoBuildIsolation() {
        return noBuildIsolation;
    }

    public void setNoBuildIsolation(boolean noBuildIsolation) {
        this.noBuildIsolation = noBuildIsolation;
    }

    @Nullable
    public String getPythonExecutablePath() {
        return pythonExecutablePath;
    }

    public void setPythonExecutablePath(@Nullable String pythonExecutablePath) {
        this.pythonExecutablePath = pythonExecutablePath;
    }

    @Nullable
    public String getJepExecutablePath() {
        return jepExecutablePath;
    }

    public void setJepExecutablePath(@Nullable String jepExecutablePath) {
        this.jepExecutablePath = jepExecutablePath;
    }
}
