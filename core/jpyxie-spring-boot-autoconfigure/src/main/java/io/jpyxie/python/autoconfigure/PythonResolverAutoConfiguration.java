package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.resolver.BasicPythonResolverHolder;
import io.jpyxie.python.resolver.PythonResolver;
import io.jpyxie.python.resolver.PythonResolverHolder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Main configuration class for {@link PythonResolver} beans.
 * <p>
 * This configuration class defines and registers various {@link PythonResolver} implementations
 * as Spring beans into {@link PythonResolverHolder} bean.
 * </p>
 * <p>
 * Each {@link PythonResolver} bean is assigned an order that determines the sequence of
 * resolver application when used collectively.
 * </p>
 * <p>
 * The configuration provides a default {@link PythonResolverHolder} bean implementation
 * {@link BasicPythonResolverHolder} if no other {@link PythonResolverHolder} bean is present
 * in the Spring context.
 * </p>
 *
 * <pre>{@code
 * // Example usage of injected PythonResolverHolder in a service:
 * @Autowired
 * private PythonResolverHolder resolverHolder;
 *
 * public String resolveScript(String script) {
 *     return resolverHolder.resolveAll(script, Map.of());
 * }
 * }</pre>
 *
 * @see PythonResolver
 * @see PythonResolverHolder
 * @see BasicPythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration
public class PythonResolverAutoConfiguration {
    /**
     * Creates the default {@link PythonResolverHolder} bean if none is defined.
     * <p>
     * This holder aggregates all available {@link PythonResolver} beans in the context.
     * </p>
     *
     * @param pythonResolvers list of all registered {@link PythonResolver} beans, never null but can be empty
     * @return a {@link BasicPythonResolverHolder} instance containing the given resolvers, never null
     */
    @Bean
    @ConditionalOnMissingBean(PythonResolverHolder.class)
    public PythonResolverHolder basicPythonResolverHolder(List<PythonResolver> pythonResolvers) {
        return new BasicPythonResolverHolder(pythonResolvers);
    }
}