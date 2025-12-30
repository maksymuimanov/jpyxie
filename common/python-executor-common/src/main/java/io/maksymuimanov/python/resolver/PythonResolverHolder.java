package io.maksymuimanov.python.resolver;

import io.maksymuimanov.python.script.PythonScript;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Container interface for managing multiple {@link PythonResolver} instances
 * and providing unified resolution of Python scripts.
 * <p>
 * This interface allows iteration, streaming, and bulk processing of
 * contained {@link PythonResolver} objects.
 * It supports composition and delegation of resolution logic in a structured and stream-friendly manner.
 * </p>
 *
 * <p><strong>Behavior of {@code resolveAll}:</strong> Applies all registered resolvers
 * to the given Python script in a defined order (implementation-dependent),
 * potentially combining or chaining their transformations.
 * The {@code argumentSpec} map is used by resolvers to substitute variables or resolve expressions.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * PythonResolverHolder holder = ...;
 * String script = "print('spel{#greeting}, spel{#name}!')";
 * Map<String, Object> args = Map.of("greeting", "Hello", "name", "World");
 * String resolved = holder.resolveAll(script, args);
 * }</pre>
 *
 * @see PythonResolver
 * @author w4t3rcs
 * @since 1.0.0
 */
public interface PythonResolverHolder extends Iterable<PythonResolver> {
    /**
     * Returns an {@link Iterator} over the registered {@link PythonResolver} instances.
     *
     * @return an iterator over resolvers (never {@code null})
     */
    @Override
    default Iterator<PythonResolver> iterator() {
        return this.getResolvers().iterator();
    }

    /**
     * Performs the given action for each registered {@link PythonResolver}.
     *
     * @param action the action to perform on each resolver (non-{@code null})
     */
    @Override
    default void forEach(Consumer<? super PythonResolver> action) {
        this.getResolvers().forEach(action);
    }

    /**
     * Returns a {@link Spliterator} over the registered {@link PythonResolver} instances.
     *
     * @return a spliterator over resolvers (never {@code null})
     */
    @Override
    default Spliterator<PythonResolver> spliterator() {
        return this.getResolvers().spliterator();
    }

    /**
     * Returns a sequential {@link Stream} of all registered {@link PythonResolver} instances.
     *
     * @return a stream of resolvers (never {@code null})
     */
    default Stream<PythonResolver> stream() {
        return this.getResolvers().stream();
    }

    /**
     * Applies all registered resolvers to the given Python script,
     * resolving variables, placeholders, or expressions with an empty argumentSpec map.
     * <p>
     * The exact order and manner in which resolvers are applied is implementation-dependent.
     * The {@code argumentSpec} map provides values for resolution and must not be {@code null}.
     * </p>
     *
     * @param script the Python script containing placeholders or expressions (non-{@code null})
     * @return the fully resolved Python script (never {@code null})
     */
    default PythonScript resolveAll(PythonScript script) {
        return this.resolveAll(script, PythonArgumentSpec.create());
    }

    /**
     * Applies all registered resolvers to the given Python script,
     * resolving variables, placeholders, or expressions using the provided argumentSpec.
     * <p>
     * The exact order and manner in which resolvers are applied is implementation-dependent.
     * The {@code argumentSpec} map provides values for resolution and must not be {@code null}.
     * </p>
     *
     * @param script the Python script containing placeholders or expressions (non-{@code null})
     * @param argumentSpec the input argumentSpec for resolution (non-{@code null}, can be empty)
     * @return the fully resolved Python script (never {@code null})
     */
    PythonScript resolveAll(PythonScript script, PythonArgumentSpec argumentSpec);

    /**
     * Returns a list of registered {@link PythonResolver} instances.
     *
     * @return a list of resolvers (never {@code null}, may be empty)
     */
    List<PythonResolver> getResolvers();
}