package io.maksymuimanov.python.resolver;

import io.maksymuimanov.python.common.Prioritized;
import io.maksymuimanov.python.script.PythonScript;

import java.util.Map;

/**
 * Defines the contract for resolving expressions and applying transformations
 * to Python scripts before execution.
 * <p>
 * Implementations of this interface perform preprocessing tasks such as
 * variable substitution, placeholder resolution, expression evaluation, or making less boilerplate
 * on the provided Python script content.
 * </p>
 *
 * <p><strong>Behavior:</strong> The method returns a transformed version of the input {@code script}
 * where all applicable expressions or placeholders are resolved using values from the {@code arguments} map.
 * The exact transformation behavior depends on the implementation.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * PythonResolver resolver = ...;
 * PythonScript script = new PythonScript("print('Hello, spel{#name}!')");
 * Map<String, Object> args = Map.of("name", "World");
 * PythonScript resolvedScript = resolver.resolve(script, args);
 * }</pre>
 *
 * @author w4t3rcs
 * @since 1.0.0
 */
public interface PythonResolver extends Prioritized {
    /**
     * Constant representing a Python JSON import statement.
     * <p>This line is automatically added to Python scripts that require JSON processing.</p>
     */
    String IMPORT_JSON = "import json";

    /**
     * Resolves the given Python script by applying transformations or expression resolution.
     *
     * @param pythonScript the Python script content to resolve (non-{@code null})
     * @param arguments    a map of variables for resolution (non-{@code null}, can be empty)
     * @return the transformed Python script with resolved expressions (never {@code null})
     */
    PythonScript resolve(PythonScript pythonScript, Map<String, Object> arguments);
}
