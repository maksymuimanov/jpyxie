package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.executor.GraalPythonExecutor;
import io.jpyxie.python.interpreter.AbstractGraalInterpreterFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.python.embedding.VirtualFileSystem;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.Map;

@Getter @Setter
@ConfigurationProperties("spring.python.executor.graalpy")
public class GraalPyProperties {
    /**
     * Whether the GraalPy executor autoconfiguration is enabled.
     */
    private boolean enabled = true;
    /**
     * Whether GraalPy sources should be cached.
     */
    private boolean cached = GraalPythonExecutor.DEFAULT_CACHED;
    /**
     * Defines host access level for GraalPy scripts.
     */
    private HostAccessHolder hostAccess = HostAccessHolder.DEFAULT;
    /**
     * Allows sharing values across contexts.
     */
    private boolean allowValueSharing = AbstractGraalInterpreterFactory.DEFAULT_ALLOW_VALUE_SHARING;
    /**
     * Allows creating subprocess.
     */
    private boolean allowCreateProcess = AbstractGraalInterpreterFactory.DEFAULT_ALLOW_CREATE_PROCESS;
    /**
     * Enables experimental options in GraalPy.
     */
    private boolean allowExperimentalOptions = AbstractGraalInterpreterFactory.DEFAULT_ALLOW_EXPERIMENTAL_OPTIONS;
    /**
     * Additional custom options for GraalPy context.
     */
    private Map<String, String> additionalOptions = AbstractGraalInterpreterFactory.DEFAULT_ADDITIONAL_OPTIONS;
    /**
     * Resources configuration for GraalPy.
     */
    private Resources resources = new Resources();
    /**
     * IO access configuration for GraalPy.
     */
    private IO io = new IO();

    @Getter
    @RequiredArgsConstructor
    public enum HostAccessHolder {
        DEFAULT(AbstractGraalInterpreterFactory.DEFAULT_HOST_ACCESS),
        ALL(HostAccess.ALL),
        EXPLICIT(HostAccess.EXPLICIT),
        SCOPED(HostAccess.SCOPED),
        CONSTRAINED(HostAccess.CONSTRAINED),
        ISOLATED(HostAccess.ISOLATED),
        UNTRUSTED(HostAccess.UNTRUSTED),
        NONE(HostAccess.NONE);

        private final HostAccess value;
    }

    /**
     * Configuration for GraalPy virtual file system resources.
     */
    @Getter @Setter
    public static class Resources {
        private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("windows");
        /**
         * Whether GraalPy virtual file system resources are enabled.
         */
        private boolean enabled = false;
        /**
         * Whether the virtual file system should be case sensitive. Default value is based on whether the OS is Windows or not.
         */
        private boolean caseSensitive = IS_WINDOWS;
        /**
         * Specifies the level of host I/O access allowed for the virtual file system.
         */
        private VirtualFileSystem.HostIO allowHostIO = VirtualFileSystem.HostIO.READ_WRITE;
        /**
         * Directory path for loading resources into the virtual file system.
         */
        @Nullable
        private String resourceDirectory;
        /**
         * Class name for custom resource loading logic.
         */
        @Nullable
        private String resourceLoadingClass;
        /**
         * Mount point path for Windows systems.
         */
        private String windowsMountPoint = "X:\\graalpy_vfs";
        /**
         * Mount point path for Unix-like systems.
         */
        private String unixMountPoint = "/graalpy_vfs";
    }

    /**
     * Configuration for GraalPy IO access.
     */
    @Getter @Setter
    public static class IO {
        /**
         * Whether to allow access to host files.
         */
        private boolean allowHostFileAccess = false;
        /**
         * Whether to allow access to host sockets.
         */
        private boolean allowHostSocketAccess = false;
    }
}
