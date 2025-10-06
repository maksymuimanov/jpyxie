package io.w4t3rcs.python.autoconfigure;

import io.w4t3rcs.python.cache.HashCacheKeyGenerator;
import io.w4t3rcs.python.executor.CachingPythonExecutor;
import io.w4t3rcs.python.file.CachingPythonFileReader;
import io.w4t3rcs.python.processor.CachingPythonProcessor;
import io.w4t3rcs.python.resolver.CachingPythonResolverHolder;
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
 * @see CachingPythonFileReader
 * @see CachingPythonResolverHolder
 * @see CachingPythonExecutor
 * @see CachingPythonProcessor
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter @Setter
@ConfigurationProperties(prefix = "spring.python.cache")
public class PythonCacheProperties {
    private boolean enabled = true;
    private PythonCacheLevel[] levels = new PythonCacheLevel[]{PythonCacheLevel.FILE, PythonCacheLevel.PROCESSOR};
    private NameProperties name = new NameProperties();
    private KeyProperties key = new KeyProperties();

    /**
     * Enumeration of available caching levels.
     * <p>
     * Determines which parts of Python processing flow are cached.
     * </p>
     */
    public enum PythonCacheLevel {
        FILE, RESOLVER, EXECUTOR, PROCESSOR
    }

    /**
     * Cache names for different cache segments.
     * <p>
     * Used to resolve cache instances by name in cache manager.
     * </p>
     */
    @Getter @Setter
    public static class NameProperties {
        private String filePaths = "pythonFilePathsCache";
        private String fileBodies = "pythonFileBodiesCache";
        private String resolver = "pythonResolverCache";
        private String executor = "pythonExecutorCache";
        private String processor = "pythonProcessorCache";
    }

    /**
     * Properties related to cache key generation.
     * <p>
     * Defines hash algorithm, charset, and delimiter used to generate cache keys.
     * </p>
     */
    @Getter @Setter
    public static class KeyProperties {
        private String hashAlgorithm = "SHA-256";
        private String charset = "UTF-8";
        private String delimiter = "_";
    }
}
