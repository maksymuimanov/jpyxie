package io.maksymuimanov.python.exception;

import io.maksymuimanov.python.cache.CacheKeyGenerator;
import io.maksymuimanov.python.cache.HashCacheKeyGenerator;

/**
 * Runtime exception thrown when cache key generation fails.
 * <p>
 * This exception wraps any underlying checked exceptions encountered during
 * the cache key generation process, such as cryptographic or encoding errors.
 * </p>
 * <p>
 * Use this exception to indicate unrecoverable errors in {@link CacheKeyGenerator} implementations.
 * </p>
 *
 * @see CacheKeyGenerator
 * @see HashCacheKeyGenerator
 * @author w4t3rcs
 * @since 1.0.0
 */
public class CacheKeyGenerationException extends PythonCacheException {
    public CacheKeyGenerationException() {
        super();
    }

    public CacheKeyGenerationException(String message) {
        super(message);
    }

    public CacheKeyGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheKeyGenerationException(Throwable cause) {
        super(cause);
    }

    public CacheKeyGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}