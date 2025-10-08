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
 *         printed: true
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
    /**
     * Python import statements required to initialize the RestrictedPython environment and access safe globals.
     */
    private String importLine = "from RestrictedPython import compile_restricted\nfrom RestrictedPython import safe_globals";
    /**
     * The variable name used in generated Python code to hold the source script string for restricted compilation.
     */
    private String codeVariableName = "source_code";
    /**
     * The name of the dictionary variable used to store execution context and results in the restricted environment.
     */
    private String localVariablesName = "execution_result";
    /**
     * Name of the variable in the Python script that holds the result value to be returned to the Java side (e.g. 'r4java').
     */
    private String resultAppearance = "r4java";
    /**
     * Whether printing via RestrictedPython’s PrintCollector should be enabled inside the sandboxed execution context.
     */
    private boolean printed = true;
}
