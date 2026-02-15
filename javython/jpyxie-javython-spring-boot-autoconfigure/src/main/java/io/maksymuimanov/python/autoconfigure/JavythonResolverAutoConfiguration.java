package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.bind.PythonSerializer;
import io.maksymuimanov.python.resolver.JavythonResolver;
import io.maksymuimanov.python.resolver.PythonResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(JavythonResolverProperties.class)
public class JavythonResolverAutoConfiguration {
    @Bean
    public PythonResolver javythonResolver(PythonSerializer pythonSerializer,
                                           JavythonResolverProperties resolverProperties) {
        return new JavythonResolver(pythonSerializer, resolverProperties.getRegex(), resolverProperties.getPositionFromStart(), resolverProperties.getPositionFromEnd());
    }
}
