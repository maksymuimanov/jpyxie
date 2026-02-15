package io.maksymuimanov.python.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.bind")
public class PythonBindProperties {
    /**
     * Implementation type to use as PythonSerializer.
     */
    private Type type;

    public enum Type {
        DICTIONARY, JSON
    }
}
