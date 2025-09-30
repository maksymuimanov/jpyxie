package io.w4t3rcs.python.properties;

import io.w4t3rcs.python.resolver.PythonResolver;
import io.w4t3rcs.python.resolver.PythonResolverHolder;
import io.w4t3rcs.python.resolver.RestrictedPythonResolver;
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
 *       script-imports-regex: (^import [\\w.]+$)|(^import [\\w.]+ as [\\w.]+$)|(^from [\\w.]+ import [\\w., ]+$)
 *       restricted:
 *         import-line: from RestrictedPython import compile_restricted
 *         code-variable-name: source_code
 *         local-variables-name: execution_result
 *         safe-result-appearance: r4java_restricted
 *         print-enabled: true
 * }</pre>
 * </p>
 *
 * @param scriptImportsRegex regular expression for matching import statements in scripts, non-null
 * @param importLine the import line required for Restricted Python integration, non-null
 * @param codeVariableName the variable name holding the code string, non-null
 * @param localVariablesName the variable name holding local variables map, non-null
 * @param printEnabled whether printing is enabled for restricted code execution
 * @see RestrictedPythonResolver
 * @see PythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@ConfigurationProperties("spring.python.resolver.restricted")
public record RestrictedPythonResolverProperties(String scriptImportsRegex,
                                                 String importLine,
                                                 String codeVariableName,
                                                 String localVariablesName,
                                                 boolean printEnabled) {
}
