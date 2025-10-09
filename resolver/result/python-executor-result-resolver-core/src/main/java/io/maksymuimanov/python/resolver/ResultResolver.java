package io.maksymuimanov.python.resolver;

import io.maksymuimanov.python.script.PythonScript;
import io.maksymuimanov.python.script.PythonScriptBuilder;
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
 * @author w4t3rcs
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class ResultResolver implements PythonResolver {
    private final String regex;
    private final String appearance;
    private final int positionFromStart;
    private final int positionFromEnd;
    private final boolean printed;

    /**
     * Resolves the script by finding and wrapping body expressions.
     *
     * @param pythonScript the original Python script content (non-null)
     * @param arguments    unused map of variables, may be null
     * @return the processed script with body expressions replaced by JSON serialization assignments
     */
    @Override
    public PythonScript resolve(PythonScript pythonScript, Map<String, Object> arguments) {
        PythonScriptBuilder builder = pythonScript.getBuilder();
        return builder.appendImport(IMPORT_JSON)
                .removeAllDeepCode(this.regex,
                        this.positionFromStart,
                        this.positionFromEnd,
                        group -> {
                    builder.appendCode(this.appearance, " = json.loads(json.dumps(", group, "))")
                            .doOnCondition(() -> {
                                builder.appendCode("print('", this.appearance, "' + json.dumps(", this.appearance, "))");
                            }, this.printed);
                })
                .build();
    }
}
