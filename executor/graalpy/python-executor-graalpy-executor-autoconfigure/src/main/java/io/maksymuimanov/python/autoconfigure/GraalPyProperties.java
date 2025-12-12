package io.maksymuimanov.python.autoconfigure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.graalvm.polyglot.HostAccess;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@Getter @Setter
@ConfigurationProperties(prefix = "spring.python.executor.graalpy")
public class GraalPyProperties {
    /**
     * Whether the GraalPy executor autoconfiguration is enabled.
     */
    private boolean enabled = true;
    /**
     * Defines the variable name of the result returned from Python to Java.
     */
    private String resultAppearance = "r4java";
    /**
     * Path to GraalPy core library.
     */
    private String coreHome = "";
    /**
     * Path to Python home or virtual environment.
     */
    private String pythonHome = "";
    /**
     * How to check .pyc hash files ("default", "never").
     */
    private String checkHashPycsMode = "default";
    /**
     * Whether to show experimental feature warnings.
     */
    private boolean warningExperimentalFeatures = false;
    /**
     * POSIX module backend ("native" or "java").
     */
    private String posixModuleBackend = "native";
    /**
     * Python executable path identifier.
     */
    private String executable = "";
    /**
     * The sys.base_prefix override.
     */
    private String sysBasePrefix = "";
    /**
     * Defines host access level for GraalPy scripts.
     */
    private HostAccessHolder hostAccess = HostAccessHolder.NONE;
    /**
     * Allows sharing values across contexts.
     */
    private boolean allowValueSharing = false;
    /**
     * Enables experimental options in GraalPy.
     */
    private boolean allowExperimentalOptions = false;
    /**
     * Additional custom options for GraalPy context.
     */
    private Properties additionalOptions = new Properties();

    @Getter
    @RequiredArgsConstructor
    public enum HostAccessHolder {
        ALL(HostAccess.ALL),
        EXPLICIT(HostAccess.EXPLICIT),
        SCOPED(HostAccess.SCOPED),
        CONSTRAINED(HostAccess.CONSTRAINED),
        ISOLATED(HostAccess.ISOLATED),
        UNTRUSTED(HostAccess.UNTRUSTED),
        NONE(HostAccess.NONE);

        private final HostAccess value;
    }
}
