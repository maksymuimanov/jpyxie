package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.cache.CacheKeyGenerator;
import io.maksymuimanov.python.cache.HashCacheKeyGenerator;
import io.maksymuimanov.python.executor.CachingPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.file.CachingPythonFileReader;
import io.maksymuimanov.python.file.PythonFileReader;
import io.maksymuimanov.python.processor.CachingPythonProcessor;
import io.maksymuimanov.python.processor.PythonProcessor;
import io.maksymuimanov.python.resolver.CachingPythonResolverHolder;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;

/**
 * Autoconfiguration class for Python caching support.
 * <p>
 * This configuration activates caching components for Python integration based on the
 * {@code spring.python.cache.enabled} property and configured cache levels.
 * </p>
 * <p>
 * Note: Property {@code spring.python.cache.enabled} must go with {@link EnableCaching} annotation.
 * Without this annotation, caching won't work
 * </p>
 * <p>
 * Provides default {@link CacheKeyGenerator} and caching wrappers for
 * {@link PythonFileReader}, {@link PythonResolverHolder}, {@link PythonExecutor}, and {@link PythonProcessor}
 * beans if present in the context.
 * </p>
 * <p>
 * Beans defined here are marked as {@code @Primary} to override default implementations
 * when caching is enabled.
 * </p>
 * <p>
 * The configuration reads default properties from {@code python-cache-default.properties}
 * located in the classpath.
 * </p>
 *
 * @see PythonCacheProperties
 * @see CacheKeyGenerator
 * @see CachingPythonFileReader
 * @see CachingPythonResolverHolder
 * @see CachingPythonExecutor
 * @see CachingPythonProcessor
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(PythonCacheProperties.class)
@ConditionalOnBooleanProperty(name = "spring.python.cache.enabled", matchIfMissing = true)
public class PythonCacheAutoConfiguration {
    /**
     * Creates the default {@link CacheKeyGenerator} bean if none is present.
     * Uses {@link HashCacheKeyGenerator} configured by {@link PythonCacheProperties}.
     *
     * @param cacheProperties non-null configuration properties for Python cache
     * @return a new instance of {@link CacheKeyGenerator}
     */
    @Bean
    @ConditionalOnMissingBean(CacheKeyGenerator.class)
    public CacheKeyGenerator cacheKeyGenerator(PythonCacheProperties cacheProperties) {
        var keyProperties = cacheProperties.getKey();
        return new HashCacheKeyGenerator(keyProperties.getHashAlgorithm(), keyProperties.getCharset(), keyProperties.getDelimiter());
    }

    /**
     * Wraps the existing {@link PythonFileReader} with caching capabilities
     * when file cache level is enabled.
     *
     * @param cacheProperties non-null Python cache configuration properties
     * @param pythonFileReader non-null delegate {@link PythonFileReader} bean
     * @param cacheManager non-null Spring cache manager for cache resolution
     * @return a caching-enabled {@link PythonFileReader} bean marked as primary
     */
    @Bean
    @Primary
    @ConditionalOnBean(PythonFileReader.class)
    @Conditional(FileCacheLevelCondition.class)
    public PythonFileReader cachingPythonFileHandler(PythonCacheProperties cacheProperties,
                                                     PythonFileReader pythonFileReader,
                                                     CacheManager cacheManager) {
        var nameProperties = cacheProperties.getName();
        return new CachingPythonFileReader(nameProperties.getFileBodies(), nameProperties.getFilePaths(), pythonFileReader, cacheManager);
    }

    /**
     * Wraps the existing {@link PythonResolverHolder} with caching capabilities
     * when resolver cache level is enabled.
     *
     * @param cacheProperties non-null Python cache configuration properties
     * @param pythonResolverHolder non-null delegate {@link PythonResolverHolder} bean
     * @param cacheManager non-null Spring cache manager
     * @param keyGenerator non-null cache key generator
     * @param objectMapper non-null JSON object mapper for serializing arguments
     * @return a caching-enabled {@link PythonResolverHolder} bean marked as primary
     */
    @Bean
    @Primary
    @ConditionalOnBean(PythonResolverHolder.class)
    @Conditional(ResolverCacheLevelCondition.class)
    public PythonResolverHolder cachingPythonResolverHolder(PythonCacheProperties cacheProperties,
                                                            PythonResolverHolder pythonResolverHolder,
                                                            CacheManager cacheManager,
                                                            CacheKeyGenerator keyGenerator,
                                                            ObjectMapper objectMapper) {
        var nameProperties = cacheProperties.getName();
        return new CachingPythonResolverHolder(nameProperties.getResolver(), pythonResolverHolder, cacheManager, keyGenerator, objectMapper);
    }

    /**
     * Wraps the existing {@link PythonExecutor} with caching capabilities
     * when executor cache level is enabled.
     *
     * @param cacheProperties non-null Python cache configuration properties
     * @param pythonExecutor non-null delegate {@link PythonExecutor} bean
     * @param cacheManager non-null Spring cache manager
     * @param keyGenerator non-null cache key generator
     * @return a caching-enabled {@link PythonExecutor} bean marked as primary
     */
    @Bean
    @Primary
    @ConditionalOnBean(PythonExecutor.class)
    @Conditional(ExecutorCacheLevelCondition.class)
    public PythonExecutor cachingPythonExecutor(PythonCacheProperties cacheProperties,
                                                PythonExecutor pythonExecutor,
                                                CacheManager cacheManager,
                                                CacheKeyGenerator keyGenerator) {
        var nameProperties = cacheProperties.getName();
        return new CachingPythonExecutor(nameProperties.getExecutor(), pythonExecutor, cacheManager, keyGenerator);
    }

    /**
     * Wraps the existing {@link PythonProcessor} with caching capabilities
     * when processor cache level is enabled.
     *
     * @param cacheProperties non-null Python cache configuration properties
     * @param pythonProcessor non-null delegate {@link PythonProcessor} bean
     * @param cacheManager non-null Spring cache manager
     * @param keyGenerator non-null cache key generator
     * @param objectMapper non-null JSON object mapper for serializing arguments
     * @return a caching-enabled {@link PythonProcessor} bean marked as primary
     */
    @Bean
    @Primary
    @ConditionalOnBean(PythonProcessor.class)
    @Conditional(ProcessorCacheLevelCondition.class)
    public PythonProcessor cachingPythonProcessor(PythonCacheProperties cacheProperties,
                                                  PythonProcessor pythonProcessor,
                                                  CacheManager cacheManager,
                                                  CacheKeyGenerator keyGenerator,
                                                  ObjectMapper objectMapper) {
        var nameProperties = cacheProperties.getName();
        return new CachingPythonProcessor(nameProperties.getProcessor(), pythonProcessor, cacheManager, keyGenerator, objectMapper);
    }
}