package io.maksymuimanov.python.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.cache.CacheKeyGenerator;
import io.maksymuimanov.python.script.PythonScript;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.TreeMap;

import static io.maksymuimanov.python.constant.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class CachingPythonResolverHolderTests {
    private CachingPythonResolverHolder cachingPythonResolverHolder;
    @Mock
    private PythonResolverHolder pythonResolverHolder;
    @Mock
    private Cache cache;
    @Mock
    private CacheKeyGenerator keyGenerator;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private PythonResolver pythonResolver;

    @BeforeEach
    void init() {
        Mockito.when(cacheManager.getCache(CACHE_MANAGER_KEY)).thenReturn(cache);
        cachingPythonResolverHolder = new CachingPythonResolverHolder(CACHE_MANAGER_KEY, pythonResolverHolder, cacheManager, keyGenerator, objectMapper);
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {
            SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3,
            RESULT_SCRIPT_0, RESULT_SCRIPT_1, RESULT_SCRIPT_2, RESULT_SCRIPT_3,
            SPELYTHON_SCRIPT_0, SPELYTHON_SCRIPT_1,
            COMPOUND_SCRIPT_0, COMPOUND_SCRIPT_1,
    })
    void testExistentKeyResolveAll(String script) {
        PythonScript pythonScript = new PythonScript(script);
        TreeMap<String, Object> sortedMap = new TreeMap<>(EMPTY_ARGUMENTS);

        Mockito.when(objectMapper.writeValueAsString(sortedMap)).thenReturn(EMPTY);
        Mockito.when(keyGenerator.generateKey(pythonScript.toString())).thenReturn(CACHE_KEY);
        Mockito.when(cache.get(CACHE_KEY, PYTHON_SCRIPT_CLASS)).thenReturn(pythonScript);

        cachingPythonResolverHolder.resolveAll(pythonScript);
        Assertions.assertNotNull(pythonScript);
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {
            SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3,
            RESULT_SCRIPT_0, RESULT_SCRIPT_1, RESULT_SCRIPT_2, RESULT_SCRIPT_3,
            SPELYTHON_SCRIPT_0, SPELYTHON_SCRIPT_1,
            COMPOUND_SCRIPT_0, COMPOUND_SCRIPT_1,
    })
    void testNonexistentKeyResolveAll(String script) {
        PythonScript pythonScript = new PythonScript(script);
        TreeMap<String, Object> sortedMap = new TreeMap<>(EMPTY_ARGUMENTS);

        Mockito.when(objectMapper.writeValueAsString(sortedMap)).thenReturn(EMPTY);
        Mockito.when(keyGenerator.generateKey(pythonScript.toString())).thenReturn(CACHE_KEY);
        Mockito.when(cache.get(CACHE_KEY, PYTHON_SCRIPT_CLASS)).thenReturn(null);
        Mockito.when(pythonResolverHolder.resolveAll(pythonScript, EMPTY_ARGUMENTS)).thenReturn(pythonScript);
        Mockito.doNothing().when(cache).put(CACHE_KEY, pythonScript);

        cachingPythonResolverHolder.resolveAll(pythonScript);
        Assertions.assertNotNull(pythonScript);
    }

    @Test
    void testGetResolvers() {
        List<PythonResolver> pythonResolvers = List.of(pythonResolver);

        Mockito.when(pythonResolverHolder.getResolvers()).thenReturn(pythonResolvers);

        Assertions.assertEquals(pythonResolvers, cachingPythonResolverHolder.getResolvers());
    }
}
