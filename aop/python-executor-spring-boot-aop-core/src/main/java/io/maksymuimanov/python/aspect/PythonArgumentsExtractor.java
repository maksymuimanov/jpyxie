package io.maksymuimanov.python.aspect;

import org.aspectj.lang.JoinPoint;

import java.util.Map;

/**
 * Interface for extracting method argumentSpec as a map of names to values from a {@link JoinPoint}.
 * <p>
 * Implementations provide the logic to extract argument names and values, optionally
 * augmented by additional argumentSpec supplied externally.
 * </p>
 * <p>
 * The default method {@link #getArguments(JoinPoint)} delegates to
 * {@link #getArguments(JoinPoint, Map)} with an empty additional argumentSpec map.
 * </p>
 *
 * @see BasicPythonArgumentsExtractor
 * @author w4t3rcs
 * @since 1.0.0
 */
public interface PythonArgumentsExtractor {
    /**
     * Extracts method argumentSpec from the given {@link JoinPoint} without additional argumentSpec.
     *
     * @param joinPoint the join point representing the method invocation, must not be {@code null}
     * @return a map of argument names to their corresponding values, never {@code null}
     */
    default Map<String, Object> getArguments(JoinPoint joinPoint) {
        return this.getArguments(joinPoint, Map.of());
    }

    /**
     * Extracts method argumentSpec from the given {@link JoinPoint} and merges them with the supplied
     * additional argumentSpec.
     *
     * @param joinPoint the join point representing the method invocation, must not be {@code null}
     * @param additionalArguments additional argumentSpec to include in the returned map, must not be {@code null}
     * @return a map of argument names to their corresponding values including additional argumentSpec, never {@code null}
     */
    Map<String, Object> getArguments(JoinPoint joinPoint, Map<String, Object> additionalArguments);
}
