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
