package io.w4t3rcs.python.properties;

import io.w4t3rcs.python.executor.PythonExecutor;
import io.w4t3rcs.python.resolver.Py4JResolver;
import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for selecting and configuring {@link PythonResolver} implementations.
 *
 * <p>These properties enable the {@link PythonExecutor} bean to correctly resolve Python scripts
 * before execution by specifying which resolvers to use and their settings.</p>
 *
 * <p>Properties are bound from the application configuration using the prefix
 * {@code spring.python.resolver}.</p>
 *
 * <p><b>Example (application.yml):</b>
 * <pre>{@code
 * spring:
 *   python:
 *     resolver:
 *       py4j:
 *         import-line: "from py4j.java_gateway import JavaGateway, GatewayParameters"
 *         gateway-object: gateway = JavaGateway(\n\tgateway_parameters=GatewayParameters(\n\t\t%s\n\t)\n)
 *         gateway-properties[0]: address=\"${spring.python.py4j.host}\"
 *         gateway-properties[1]: port=${spring.python.py4j.port}
 *         gateway-properties[2]: auth_token=\"${spring.python.py4j.auth-token}\"
 *         gateway-properties[3]: auto_convert=True
 * }</pre>
 * </p>
 *
 * @param importLine the import line required for Py4J integration, non-null
 * @param gatewayObject the name of the Py4J gateway object, non-null
 * @param gatewayProperties array of gateway property names, non-null, may be empty
 * @see Py4JResolver
 * @see PythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@ConfigurationProperties("spring.python.resolver.py4j")
public record Py4JResolverProperties(String importLine, String gatewayObject, String[] gatewayProperties) {
}
