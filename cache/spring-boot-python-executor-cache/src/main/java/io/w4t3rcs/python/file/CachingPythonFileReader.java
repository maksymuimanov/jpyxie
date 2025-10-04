package io.w4t3rcs.python.file;

import io.w4t3rcs.python.exception.PythonCacheException;
import io.w4t3rcs.python.properties.PythonCacheProperties;
import io.w4t3rcs.python.script.PythonScript;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.nio.file.Path;
import java.util.Objects;

/**
 * {@link PythonFileReader} implementation that adds caching capabilities
 * for script file paths and script bodies.
 * <p>
 * This handler delegates all operations to a wrapped {@link PythonFileReader} instance,
 * caching file paths and script contents to improve performance for repeated access.
 * </p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * PythonFileReader baseReader = ...;
 * CacheManager cacheManager = ...;
 * PythonCacheProperties cacheProperties = ...;
 * PythonFileReader cachingReader = new CachingPythonFileReader(cacheProperties, baseReader, cacheManager);
 *
 * PythonScript script = ...;
 * String scriptBody = cachingReader.readScript(script);
 * }</pre>
 *
 * @see PythonFileReader
 * @see PythonCacheProperties.NameProperties
 * @author w4t3rcs
 * @since 1.0.0
 */
public class CachingPythonFileReader implements PythonFileReader {
    private final PythonFileReader pythonFileReader;
    private final Cache scriptBodyCache;
    private final Cache pathCache;

    /**
     * Constructs a new {@code CachingPythonFileHandler}.
     *
     * @param cacheProperties non-null cache properties providing cache names
     * @param pythonFileReader non-null delegate {@link PythonFileReader} instance
     * @param cacheManager non-null {@link CacheManager} to obtain cache instances
     */
    public CachingPythonFileReader(PythonCacheProperties cacheProperties, PythonFileReader pythonFileReader, CacheManager cacheManager) {
        this.pythonFileReader = pythonFileReader;
        var nameProperties = cacheProperties.name();
        this.pathCache = Objects.requireNonNull(cacheManager.getCache(nameProperties.filePaths()));
        this.scriptBodyCache = Objects.requireNonNull(cacheManager.getCache(nameProperties.fileBodies()));
    }

    /**
     * Reads the script body from the file at the specified path.
     * <p>
     * Results are cached by {@link Path}. If a cached value is present, it is returned.
     * Otherwise, the script is read from the underlying handler and cached.
     * </p>
     *
     * @param pythonScript non-null {@link PythonScript} container of the script file path
     * @return non-null script {@link PythonScript}
     * @throws PythonCacheException if any underlying error occurs during caching or reading
     */
    @Override
    public PythonScript readScript(PythonScript pythonScript) {
        try {
            String source = Objects.requireNonNull(pythonScript.getSource());
            PythonScript cachedScriptBody = scriptBodyCache.get(source, PythonScript.class);
            if (cachedScriptBody != null) return cachedScriptBody;
            pythonFileReader.readScript(pythonScript);
            scriptBodyCache.put(source, pythonScript);
            return pythonScript;
        } catch (Exception e) {
            throw new PythonCacheException(e);
        }
    }

    /**
     * Resolves the full {@link Path} to the script file given a string path.
     * <p>
     * The resolved path is cached. If present in cache, the cached path is returned.
     * Otherwise, the path is resolved by the underlying handler and cached before returning.
     * </p>
     *
     * @param path non-null string path to resolve
     * @return non-null resolved {@link Path}
     * @throws PythonCacheException if any underlying error occurs during caching or resolution
     */
    @Override
    public Path getScriptPath(String path) {
        try {
            Path cachedPath = pathCache.get(path, Path.class);
            if (cachedPath != null) return cachedPath;
            Path fullPath = pythonFileReader.getScriptPath(path);
            pathCache.put(path, fullPath);
            return fullPath;
        } catch (Exception e) {
            throw new PythonCacheException(e);
        }
    }
}