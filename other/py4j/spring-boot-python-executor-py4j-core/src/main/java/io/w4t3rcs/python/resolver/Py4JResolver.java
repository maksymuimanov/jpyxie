package io.w4t3rcs.python.resolver;

import io.w4t3rcs.python.properties.Py4JResolverProperties;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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
 * @see AbstractPythonResolver
 * @see PythonResolverHolder
 * @see Py4JResolverProperties
 * @author w4t3rcs
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class Py4JResolver extends AbstractPythonResolver {
    private final Py4JResolverProperties resolverProperties;

    /**
     * Resolves a Python script by inserting necessary Py4J import statements and gateway initialization lines.
     * Removes duplicate or existing import lines matching the configured regex before reinserting them.
     *
     * @param script non-null Python script content
     * @param arguments ignored in this implementation, may be null
     * @return non-null resolved script containing Py4J import statements and gateway setup
     */
    @Override
    public String resolve(String script, Map<String, Object> arguments) {
        StringBuilder resolvedScript = new StringBuilder(script);
        List<String> importLines = new ArrayList<>();
        this.removeScriptLines(resolvedScript, resolverProperties.scriptImportsRegex(),
                (matcher, fragment) -> importLines.add(fragment));
        String gatewayObject = resolverProperties.gatewayObject();
        String gatewayProperties = String.join(",\n\t\t", resolverProperties.gatewayProperties());
        this.insertUniqueLineToStart(resolvedScript, gatewayObject.formatted(gatewayProperties));
        for (int i = importLines.size() - 1; i >= 0; i--) {
            this.insertUniqueLineToStart(resolvedScript, importLines.get(i));
        }
        this.insertUniqueLineToStart(resolvedScript, resolverProperties.importLine());
        return resolvedScript.toString();
    }
}
