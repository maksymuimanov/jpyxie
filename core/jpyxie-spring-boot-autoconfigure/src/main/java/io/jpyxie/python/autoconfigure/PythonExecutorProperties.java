package io.jpyxie.python.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.executor")
public class PythonExecutorProperties {
    /**
     * Defines how Python execution results are represented or converted on the Java side.
     */
    private String resultAppearance = "r4java";
}
