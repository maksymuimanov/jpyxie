package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.bind.PythonSerializer;
import io.jpyxie.python.resolver.PythonResolver;
import io.jpyxie.python.resolver.PythonResolverHolder;
import io.jpyxie.python.resolver.SpelythonResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Main configuration class for {@link SpelythonResolver}.
 * <p>
 * This configuration class defines and registers {@link SpelythonResolver} as Spring bean
 * </p>
 * <p>
 * Each {@link PythonResolver} bean is assigned an order that determines the sequence of
 * resolver application when used collectively.
 * </p>
 * <p>
 *
 * @see PythonResolver
 * @see SpelythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(SpelythonResolverProperties.class)
public class SpelythonResolverAutoConfiguration {
    @Bean
    public PythonResolver spelythonResolver(PythonSerializer pythonSerializer,
                                            ApplicationContext applicationContext,
                                            SpelythonResolverProperties resolverProperties) {
        return new SpelythonResolver(pythonSerializer, applicationContext, resolverProperties.getRegex(), resolverProperties.getLocalVariableIndex(), resolverProperties.getPositionFromStart(), resolverProperties.getPositionFromEnd());
    }
}