package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.resolver.Py4JResolver;
import io.maksymuimanov.python.resolver.PythonResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import py4j.CallbackClient;
import py4j.GatewayServer;

import javax.net.ServerSocketFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Spring {@link Configuration @Configuration} class for configuring Py4J integration.
 * <p>
 * This configuration creates and starts a {@link GatewayServer} that enables
 * bidirectional communication between Java and Python through the Py4J protocol.
 * </p>
 *
 * <p>The configuration is activated only when the property {@code spring.python.py4j.enabled=true} is set.
 * All properties are read from {@link Py4JProperties}.</p>
 * @see Py4JProperties
 * @see Py4JResolver
 * @see GatewayServer
 * @see CallbackClient
 * @see <a href="https://www.py4j.org/">Py4J Documentation</a>
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration
@ConditionalOnBooleanProperty(name = "spring.python.py4j.enabled", matchIfMissing = true)
@EnableConfigurationProperties({
        Py4JProperties.class,
        Py4JResolverProperties.class
})
public class Py4JAutoConfiguration {
    /**
     * Creates and starts a {@link GatewayServer} bean for Py4J-based Java–Python integration.
     *
     * <p>The {@link GatewayServer} is configured based on the provided {@link Py4JProperties}.
     * It is started immediately after creation. If {@code loggable} is set to {@code true},
     * Py4J internal logging is enabled.</p>
     *
     * @param py4JProperties non-null configuration properties for the Py4J integration
     * @return a started {@link GatewayServer} instance
     * @throws GatewayServerCreationException if the host address cannot be resolved
     */
    @Bean
    @ConditionalOnMissingBean(GatewayServer.class)
    public GatewayServer py4JGatewayServer(Py4JProperties py4JProperties) {
        if (py4JProperties.isLoggable()) GatewayServer.turnAllLoggingOn();
        try {
            CallbackClient pythonClient = new CallbackClient(py4JProperties.getPythonPort(), InetAddress.getByName(py4JProperties.getPythonHost()), py4JProperties.getAuthToken());
            GatewayServer server = new GatewayServer.GatewayServerBuilder()
                    .entryPoint(null)
                    .javaPort(py4JProperties.getPort())
                    .javaAddress(InetAddress.getByName(py4JProperties.getHost()))
                    .callbackClient(pythonClient)
                    .connectTimeout(py4JProperties.getConnectTimeout())
                    .readTimeout(py4JProperties.getReadTimeout())
                    .serverSocketFactory(ServerSocketFactory.getDefault())
                    .customCommands(null)
                    .authToken(py4JProperties.getAuthToken())
                    .build();
            server.start();
            return server;
        } catch (UnknownHostException e) {
            throw new GatewayServerCreationException(e);
        }
    }

    /**
     * Creates a {@link Py4JResolver} bean.
     *
     * @param resolverProperties {@link Py4JResolverProperties} bean, must not be null
     * @return configured {@link Py4JResolver} instance, never null
     */
    @Bean
    @ConditionalOnProperty(name = "spring.python.py4j.enabled", havingValue = "true")
    public PythonResolver py4JResolver(Py4JResolverProperties resolverProperties) {
        return new Py4JResolver(resolverProperties.getGatewayObject(), resolverProperties.getGatewayProperties(), resolverProperties.getImportLine());
    }
}
