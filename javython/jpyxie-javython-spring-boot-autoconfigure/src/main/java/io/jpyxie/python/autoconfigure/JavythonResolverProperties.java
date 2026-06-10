package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.resolver.JavythonResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.resolver.javython")
public class JavythonResolverProperties {
    /**
     * Regular expression pattern used to locate SpEL expressions within Python scripts.
     */
    private String regex = JavythonResolver.DEFAULT_REGEX;
}
