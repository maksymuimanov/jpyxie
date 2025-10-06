package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import io.w4t3rcs.python.resolver.RestrictedPythonResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for configuring {@link RestrictedPythonResolver}.
 *
 * <p>Properties are bound from the application configuration using the prefix
 * {@code spring.python.resolver.restricted}.</p>
 *
 * <p><b>Example (application.yml):</b>
 * <pre>{@code
 * spring:
 *   python:
 *     resolver:
 *       restricted:
 *         import-line: from RestrictedPython import compile_restricted
 *         code-variable-name: source_code
 *         local-variables-name: execution_result
 *         safe-result-appearance: r4java_restricted
 *         print-enabled: true
 * }</pre>
 * </p>
 *
 * @see RestrictedPythonResolver
 * @see PythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties("spring.python.resolver.restricted")
public class RestrictedPythonResolverProperties {
    private String importLine = "from RestrictedPython import compile_restricted\nfrom RestrictedPython import safe_globals";
    private String codeVariableName = "source_code";
    private String localVariablesName = "execution_result";
    private String resultAppearance = "r4java";
    private boolean printEnabled = true;
}
