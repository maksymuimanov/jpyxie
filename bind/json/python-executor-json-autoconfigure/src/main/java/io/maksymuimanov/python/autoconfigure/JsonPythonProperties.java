package io.maksymuimanov.python.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.executor.process")
public class JsonPythonProperties {
    /**
     * Whether to enable caching of JSON trees.
     */
    private boolean cached = true;
}
