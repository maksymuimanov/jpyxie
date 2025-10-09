package io.maksymuimanov.python.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.maksymuimanov.python.constant.TestConstants.OK;

class HashCacheKeyGeneratorTests {
    private final CacheKeyGenerator keyGenerator = new HashCacheKeyGenerator(this.hashAlgorithm, this.charset, this.delimiter);
    private final String hashAlgorithm = "SHA-256";
    private final String charset = "UTF-8";
    private final String delimiter = "_";

    @Test
    void testGenerateKey() {
        String prefix = "prefix";
        String suffix = "suffix";
        String hashedBody = "VlM5vE0z1ygXtYMCQRLrf1zfPl7vAlLW7BucmpThK7M=";

        String generated = keyGenerator.generateKey(prefix, OK, suffix);
        Assertions.assertTrue(generated.contains("_"));
        Assertions.assertTrue(generated.startsWith(prefix));
        Assertions.assertTrue(generated.endsWith(suffix));
        Assertions.assertTrue(generated.contains(hashedBody));
        Assertions.assertFalse(generated.contains(OK));
    }
}
