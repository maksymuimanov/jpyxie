package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.cache.CacheKeyGenerator;
import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Objects;

/**
 * {@link PythonExecutor} implementation that adds caching capabilities.
 * <p>
 * This executor delegates script execution to a wrapped {@link PythonExecutor} instance,
 * caching results based on generated cache keys to improve performance on repeated executions
 * with identical scripts and body types.
 * </p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * PythonExecutor baseExecutor = ...;
 * CacheKeyGenerator keyGenerator = ...;
 * CacheManager cacheManager = ...;
 * String cacheName = ...;
 * PythonExecutor cachingExecutor = new CachingPythonExecutor(cacheName, baseExecutor, cacheManager, keyGenerator);
 *
 * String script = "print('hello')";
 * cachingExecutor.execute(script, null);
 * }</pre>
 *
 * @see PythonExecutor
 * @see CacheKeyGenerator
 * @author w4t3rcs
 * @since 1.0.0
 */
public class CachingPythonExecutor implements PythonExecutor {
    private final PythonExecutor pythonExecutor;
    private final Cache cache;
    private final CacheKeyGenerator keyGenerator;

    /**
     * Constructs a new {@code CachingPythonExecutor}.
     *
     * @param cacheName non-null cache name
     * @param pythonExecutor non-null delegate {@link PythonExecutor} for actual script execution
     * @param cacheManager non-null {@link CacheManager} used to obtain the {@link Cache} instance
     * @param keyGenerator non-null {@link CacheKeyGenerator} for generating cache keys
     */
    public CachingPythonExecutor(String cacheName, PythonExecutor pythonExecutor, CacheManager cacheManager, CacheKeyGenerator keyGenerator) {
        this.pythonExecutor = pythonExecutor;
        this.cache = Objects.requireNonNull(cacheManager.getCache(cacheName));
        this.keyGenerator = keyGenerator;
    }

    /**
     * Executes the given Python script and returns the body of the specified type.
     * <p>
     * If a cached body is available for the generated cache key, it is returned immediately.
     * Otherwise, the script is executed using the delegate {@link PythonExecutor},
     * and the body is cached before returning.
     * </p>
     * <p>
     * Cache keys are generated using the configured {@link CacheKeyGenerator} with the script and body class.
     * </p>
     *
     * @param <R> the expected body type
     * @param script non-null Python script to execute
     * @param resultClass nullable {@link Class} representing the expected body type
     * @return the execution body, guaranteed non-null if the delegate returns non-null
     * @throws PythonExecutionException if any caching or execution error occurs
     */
    @Override
    @SuppressWarnings("unchecked")
    public <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass) {
        try {
            String scriptBody = script.toString();
            String key = keyGenerator.generateKey(scriptBody, resultClass);
            PythonExecutionResponse<R> cachedResult = (PythonExecutionResponse<R>) cache.get(key, PythonExecutionResponse.class);
            if (cachedResult != null) return cachedResult;
            PythonExecutionResponse<R> result = pythonExecutor.execute(script, resultClass);
            cache.put(key, result);
            return result;
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }
}