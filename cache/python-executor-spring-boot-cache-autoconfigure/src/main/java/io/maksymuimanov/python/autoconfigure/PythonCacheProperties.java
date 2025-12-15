package io.maksymuimanov.python.autoconfigure;

import io.maksymuimanov.python.cache.HashCacheKeyGenerator;
import io.maksymuimanov.python.executor.CachingPythonExecutor;
import io.maksymuimanov.python.processor.CachingPythonProcessor;
import io.maksymuimanov.python.resolver.CachingPythonResolverHolder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Python caching functionality.
 * <p>
 * Maps properties under the prefix {@code spring.python.cache} to configure
 * caching behavior including enabling/disabling cache, cache levels, cache
 * names for different cache types, and key generation properties.
 * </p>
 *
 * <p>Example configuration in application.yml:</p>
 * <pre>{@code
 * spring:
 *   python:
 *     cache:
 *       enabled: true
 *       levels: file, processor
 *       name:
 *         file-paths: filePathsCache
 *         file-bodies: fileBodiesCache
 *         resolver: pythonResolverCache
 *         executor: pythonExecutorCache
 *         processor: pythonProcessorCache
 *       key:
 *         hashAlgorithm: SHA-256
 *         charset: UTF-8
 *         delimiter: _
 * }</pre>
 *
 * @see HashCacheKeyGenerator
 * @see CachingPythonResolverHolder
 * @see CachingPythonExecutor
 * @see CachingPythonProcessor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties(prefix = "spring.python.cache")
public class PythonCacheProperties {
    /**
     * Whether the Python cache autoconfiguration is enabled.
     */
    private boolean enabled = true;
    /**
     * Defines which parts of the Python processing flow are cached.
     */
    private PythonCacheLevel[] levels = new PythonCacheLevel[]{PythonCacheLevel.PROCESSOR};
    /**
     * Defines a cache name for each cache segment.
     */
    private String name = "pythonCache";
    /**
     * Configuration properties defining cache key generation behavior.
     */
    private KeyProperties key = new KeyProperties();

    /**
     * Enumeration of available caching levels.
     * <p>
     * Determines which parts of Python processing flow are cached.
     * </p>
     */
    public enum PythonCacheLevel {
        RESOLVER, EXECUTOR, PROCESSOR
    }

    /**
     * Properties related to cache key generation.
     * <p>
     * Defines hash algorithm, charset, and delimiter used to generate cache keys.
     * </p>
     */
    @Getter @Setter
    public static class KeyProperties {
        /**
         * Hash algorithm used to generate cache keys (e.g., SHA-256, MD5).
         */
        private String hashAlgorithm = "SHA-256";
        /**
         * Charset used to encode cache key components before hashing.
         */
        private String charset = "UTF-8";
        /**
         * Delimiter used between cache key parts during concatenation.
         */
        private String delimiter = "_";
    }
}
