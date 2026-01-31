package io.maksymuimanov.python.resolver;

import io.maksymuimanov.python.exception.PythonScriptException;
import io.maksymuimanov.python.script.PythonScript;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link PythonResolverHolder} that holds a list of {@link PythonResolver}
 * and applies them sequentially to a Python script.
 * <p>
 * This class enables step-by-step transformation of the script through a chain of resolvers.
 * It is immutable - the list of resolvers is provided via constructor and does not change.
 * </p>
 * <p>
 * Usage example:
 * <pre>{@code
 * List<PythonResolver> resolvers = List.of(new SpelythonResolver(...), new Py4JResolver(...), ...);
 * PythonResolverHolder holder = new BasicPythonResolverHolder(resolvers);
 * String script = "...";
 * Map<String, Object> args = Map.of("key", value);
 * String resolved = holder.resolveAll(script, args);
 * }</pre>
 * </p>
 *
 * @see PythonResolverHolder
 * @see PythonResolver
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
public class BasicPythonResolverHolder implements PythonResolverHolder {
    /**
     * The list of resolvers used to process the script sequentially.
     * Cannot be {@code null}. It is recommended to use an unmodifiable list.
     */
    private final List<PythonResolver> pythonResolvers;

    public BasicPythonResolverHolder(List<PythonResolver> pythonResolvers) {
        this.pythonResolvers = pythonResolvers;
        Collections.sort(this.pythonResolvers);
    }

    /**
     * Sequentially applies all {@link PythonResolver} instances from {@link #getResolvers()} to the input script.
     * <p>
     * Returns the final script after all resolvers have been applied in order.
     * <p>
     * Behavior for {@code argumentSpec} depends on individual resolver implementations.
     * This method does not modify the original script but creates new string instances on each iteration.
     * </p>
     *
     * @param script the original Python script, must be non-null and non-empty
     * @param argumentSpec map of argumentSpec passed to resolvers, can be {@code null} or empty
     * @return the fully resolved script, never {@code null}
     * @throws IllegalArgumentException if {@code script} is {@code null} or empty
     */
    @Override
    public PythonScript resolveAll(PythonScript script, PythonArgumentSpec argumentSpec) {
        try {
            for (PythonResolver resolver : this.getResolvers()) {
                resolver.resolve(script, argumentSpec);
            }
            return script;
        } catch (Exception e) {
            throw new PythonScriptException(e);
        }
    }

    /**
     * Returns the list of resolvers registered in this holder.
     * <p>
     * The returned list may or may not be thread-safe or modifiable,
     * depending on the list passed to the constructor.
     * Modifications to the list outside this class will affect this holder's behavior.
     * </p>
     *
     * @return the list of {@link PythonResolver}, never {@code null}
     */
    @Override
    public List<PythonResolver> getResolvers() {
        return pythonResolvers;
    }
}