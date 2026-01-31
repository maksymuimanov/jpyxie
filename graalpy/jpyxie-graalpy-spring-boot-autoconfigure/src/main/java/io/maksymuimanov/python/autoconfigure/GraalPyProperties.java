package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.executor.GraalPythonExecutor;
import io.maksymuimanov.python.interpreter.GraalInterpreterFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.graalvm.polyglot.HostAccess;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter @Setter
@ConfigurationProperties(prefix = "spring.python.executor.graalpy")
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
    private boolean allowValueSharing = GraalInterpreterFactory.DEFAULT_ALLOW_VALUE_SHARING;
    /**
     * Enables experimental options in GraalPy.
     */
    private boolean allowExperimentalOptions = GraalInterpreterFactory.DEFAULT_ALLOW_EXPERIMENTAL_OPTIONS;
    /**
     * Additional custom options for GraalPy context.
     */
    private Map<String, String> additionalOptions = GraalInterpreterFactory.DEFAULT_ADDITIONAL_OPTIONS;

    @Getter
    @RequiredArgsConstructor
    public enum HostAccessHolder {
        DEFAULT(GraalInterpreterFactory.DEFAULT_HOST_ACCESS),
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
