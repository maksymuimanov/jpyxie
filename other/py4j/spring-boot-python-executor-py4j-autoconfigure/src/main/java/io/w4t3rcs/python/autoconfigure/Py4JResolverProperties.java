package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.executor.PythonExecutor;
import io.w4t3rcs.python.resolver.Py4JResolver;
import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import lombok.Getter;
import lombok.Setter;
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
 * @author w4t3rcs
 * @see Py4JResolver
 * @see PythonResolver
 * @see PythonResolverHolder
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.resolver.py4j")
public class Py4JResolverProperties {
    private String importLine = "from py4j.java_gateway import JavaGateway, GatewayParameters";
    private String gatewayObject = "gateway = JavaGateway(\n\tgateway_parameters=GatewayParameters(\n\t\t%s\n\t)\n)";
    private String[] gatewayProperties = new String[]{
            "address=\"${spring.python.py4j.host}\"",
            "port=${spring.python.py4j.port}",
            "auth_token=\"${spring.python.py4j.auth-token}\"",
            "auto_convert=True"
    };
}
