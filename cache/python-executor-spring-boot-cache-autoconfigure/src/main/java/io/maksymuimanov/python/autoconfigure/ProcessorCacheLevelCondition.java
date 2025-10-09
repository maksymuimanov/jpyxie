package io.maksymuimanov.python.autoconfigure;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * {@link AbstractCacheLevelCondition} implementation that matches when
 * the {@code spring.python.cache.levels} property contains the cache level {@link PythonCacheProperties.PythonCacheLevel#PROCESSOR}.
 * <p>
 * This condition is used to enable caching features related to processor-level caching.
 * </p>
 * <p>
 * The cache level is fixed to {@link PythonCacheProperties.PythonCacheLevel#PROCESSOR}.
 * </p>
 *
 * @see AbstractCacheLevelCondition
 * @see PythonCacheProperties.PythonCacheLevel#PROCESSOR
 * @see PythonCacheAutoConfiguration
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter(AccessLevel.PROTECTED)
public class ProcessorCacheLevelCondition extends AbstractCacheLevelCondition {
    private final PythonCacheProperties.PythonCacheLevel cacheLevel = PythonCacheProperties.PythonCacheLevel.PROCESSOR;
}
