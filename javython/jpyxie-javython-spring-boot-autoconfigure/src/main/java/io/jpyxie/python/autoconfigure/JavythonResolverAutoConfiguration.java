package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.resolver.JavythonResolver;
import io.jpyxie.python.resolver.PythonResolver;
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
