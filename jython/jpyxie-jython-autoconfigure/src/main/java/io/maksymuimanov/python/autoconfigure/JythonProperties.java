package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.lifecycle.JythonInitializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "spring.python.executor.jython")
public class JythonProperties {
    /**
     * Whether the Jython executor autoconfiguration is enabled.
     */
    private boolean enabled = true;
    /**
     * Specifies the path to the Python Home directory.
     */
    private String pythonHome = "";
    /**
     * Determines whether the default Python library site modules should be imported when initializing the Python interpreter.
     */
    private boolean importSite = JythonInitializer.DEFAULT_IMPORT_SITE;
}
