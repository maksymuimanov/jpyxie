package io.jpyxie.python.library;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class JepLibraryManagement extends PythonLibraryManagement {
    public static final String JEP_LIBRARY_NAME = "jep";
    public static final String NO_CACHE_DIR_OPTION = "--no-cache-dir";
    public static final String NO_BUILD_ISOLATION_OPTION = "--no-build-isolation";
    public static final boolean DEFAULT_NO_CACHE_DIR = true;
    public static final boolean DEFAULT_NO_BUILD_ISOLATION = true;
    private boolean noCacheDir = DEFAULT_NO_CACHE_DIR;
    private boolean noBuildIsolation = DEFAULT_NO_BUILD_ISOLATION;
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
        this(name, new ArrayList<>());
    }

    public JepLibraryManagement(String name, List<String> options) {
        super(name, options);
        if (this.isNoCacheDir()) this.addOption(NO_CACHE_DIR_OPTION);
        if (this.isNoBuildIsolation()) this.addOption(NO_BUILD_ISOLATION_OPTION);
    }
}
