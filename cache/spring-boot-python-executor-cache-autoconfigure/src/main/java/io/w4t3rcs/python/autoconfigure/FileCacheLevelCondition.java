package io.w4t3rcs.python.autoconfigure;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * {@link AbstractCacheLevelCondition} implementation that matches when
 * the {@code spring.python.cache.levels} property contains the cache level {@link PythonCacheProperties.PythonCacheLevel#FILE}.
 * <p>
 * This condition is used to enable caching features related to file-level caching.
 * </p>
 * <p>
 * The cache level is fixed to {@link PythonCacheProperties.PythonCacheLevel#FILE}.
 * </p>
 *
 * @see AbstractCacheLevelCondition
 * @see PythonCacheProperties.PythonCacheLevel#FILE
 * @see PythonCacheAutoConfiguration
 * @author w4t3rcs
 * @since 1.0.0
 */
@Getter(AccessLevel.PROTECTED)
public class FileCacheLevelCondition extends AbstractCacheLevelCondition {
    private final PythonCacheProperties.PythonCacheLevel cacheLevel = PythonCacheProperties.PythonCacheLevel.FILE;
}