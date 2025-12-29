package io.maksymuimanov.python.resolver;

import io.maksymuimanov.python.script.PythonScript;

import java.util.Map;

/**
 * {@link PythonResolver} implementation that prepares Python scripts for integration
 * with Java using the Py4J bridge.
 *
 * <p>This resolver adds necessary import statements and gateway object initialization
 * lines to the Python script to enable communication between Java and Python via Py4J.</p>
 *
 * <p>The resolver first removes existing import lines matching the configured regex,
 * stores them, and then re-inserts them at the start of the script in the correct order, ensuring no duplicates.
 * It also inserts the gateway object initialization line as specified in the configuration.</p>
 *
 * <p>This resolver is enabled conditionally based on the {@code Py4JCondition}.</p>
 *
 * @see PythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
public class Py4JResolver implements PythonResolver {
    public static final int PRIORITY = -50;
    private final String gatewayObject;
    private final String importLine;

    public Py4JResolver(String gatewayObject, String[] gatewayProperties, String importLine) {
        String properties = String.join(",\n\t\t", gatewayProperties);
        this.gatewayObject = gatewayObject.formatted(properties);
        this.importLine = importLine;
    }

    /**
     * Resolves a Python script by inserting necessary Py4J import statements and gateway initialization lines.
     * Removes duplicate or existing import lines matching the configured regex before reinserting them.
     *
     * @param pythonScript non-null Python script content
     * @param arguments    ignored in this implementation, may be null
     * @return non-null resolved script containing Py4J import statements and gateway setup
     */
    @Override
    public PythonScript resolve(PythonScript pythonScript, Map<String, Object> arguments) {
        return pythonScript.getBuilder()
                .appendImport(importLine)
                .prependCode(gatewayObject)
                .getScript();
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
