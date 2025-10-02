package io.w4t3rcs.python.file;

import io.w4t3rcs.python.properties.PythonCacheProperties;
import io.w4t3rcs.python.script.PythonScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static io.w4t3rcs.python.constant.TestConstants.*;
import static io.w4t3rcs.python.properties.PythonCacheProperties.NameProperties;

@ExtendWith(MockitoExtension.class)
class CachingPythonFileReaderTests {
    private CachingPythonFileReader cachingPythonFileHandler;
    @Mock
    private PythonFileReader pythonFileReader;
    @Mock
    private Cache scriptBodyCache;
    @Mock
    private Cache pathCache;
    @Mock
    private PythonCacheProperties cacheProperties;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private NameProperties nameProperties;

    @BeforeEach
    void init() {
        Mockito.when(cacheProperties.name()).thenReturn(nameProperties);
        Mockito.when(nameProperties.fileBodies()).thenReturn(CACHE_MANAGER_KEY + "0");
        Mockito.when(nameProperties.filePaths()).thenReturn(CACHE_MANAGER_KEY + "1");
        Mockito.when(cacheManager.getCache(CACHE_MANAGER_KEY + "0")).thenReturn(scriptBodyCache);
        Mockito.when(cacheManager.getCache(CACHE_MANAGER_KEY + "1")).thenReturn(pathCache);
        cachingPythonFileHandler = new CachingPythonFileReader(cacheProperties, pythonFileReader, cacheManager);
    }

    @Test
    void testExistentKeyReadScript() {
        PythonScript pythonScript = new PythonScript(FILE_SCRIPT);
        String source = pythonScript.getSource();

        Mockito.when(scriptBodyCache.get(source, PYTHON_SCRIPT_CLASS)).thenReturn(pythonScript);

        cachingPythonFileHandler.readScript(pythonScript);
        Assertions.assertNotNull(pythonScript);
    }

    @Test
    void testNonexistentKeyReadScript() {
        PythonScript pythonScript = new PythonScript(FILE_SCRIPT);
        String source = pythonScript.getSource();

        Mockito.when(scriptBodyCache.get(source, PYTHON_SCRIPT_CLASS)).thenReturn(null);
        Mockito.when(pythonFileReader.readScript(pythonScript)).thenReturn(pythonScript);
        Mockito.doNothing().when(scriptBodyCache).put(source, pythonScript);

        cachingPythonFileHandler.readScript(pythonScript);
        Assertions.assertNotNull(pythonScript);
    }
}
