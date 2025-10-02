package io.w4t3rcs.python.resolver;

import io.w4t3rcs.python.properties.ResultResolverProperties;
import io.w4t3rcs.python.script.PythonScript;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * {@link PythonResolver} implementation that processes body expressions in Python scripts.
 *
 * <p>This resolver searches for body expressions in the script using a configured regex pattern
 * and wraps each found expression with JSON serialization logic, assigning it to a configured
 * body variable.</p>
 *
 * <p>The processed script can then expose evaluated body data in a JSON-compatible form.</p>
 *
 * @see PythonResolver
 * @see PythonResolverHolder
 * @see ResultResolverProperties
 * @author w4t3rcs
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class ResultResolver implements PythonResolver {
    private final ResultResolverProperties resolverProperties;

    /**
     * Resolves the script by finding and wrapping body expressions.
     *
     * @param script the original Python script content (non-null)
     * @param arguments unused map of variables, may be null
     * @return the processed script with body expressions replaced by JSON serialization assignments
     */
    @Override
    public PythonScript resolve(PythonScript script, Map<String, Object> arguments) {
        String resultAppearance = resolverProperties.appearance();
        script.appendImport(IMPORT_JSON);
        script.removeAll(resolverProperties.regex(),
                resolverProperties.positionFromStart(),
                resolverProperties.positionFromEnd(),
                group -> {
            script.appendCode(resultAppearance, " = json.loads(json.dumps(", group, "))");
            if (resolverProperties.isPrinted()) {
                script.appendCode("print('", resultAppearance, "' + json.dumps(", resultAppearance, "))");
            }
        });
        return script;
    }
}
