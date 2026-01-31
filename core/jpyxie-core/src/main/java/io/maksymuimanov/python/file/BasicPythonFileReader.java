package io.maksymuimanov.python.file;

import io.maksymuimanov.python.exception.PythonFileException;
import io.maksymuimanov.python.script.BasicPythonScriptBuilder;
import io.maksymuimanov.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the {@link PythonFileReader} interface providing
 * reading file operations for Python scripts.
 * <p>
 * This class supports reading from Python script files and creating script input
 * stream based on configured {@link BasicPythonFileReader#inputStreamProvider}.
 * </p>
 *
 * @see PythonFileReader
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class BasicPythonFileReader implements PythonFileReader {
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private final Map<String, String> fileCache;
    private final InputStreamProvider inputStreamProvider;
    private final Charset charset;

    public BasicPythonFileReader(InputStreamProvider inputStreamProvider) {
        this(inputStreamProvider, DEFAULT_CHARSET_NAME);
    }

    public BasicPythonFileReader(InputStreamProvider inputStreamProvider, String charsetName) {
        this(inputStreamProvider, Charset.forName(charsetName));
    }

    public BasicPythonFileReader(InputStreamProvider inputStreamProvider, Charset charset) {
        this(new ConcurrentHashMap<>(), inputStreamProvider, charset);
    }

    /**
     * Reads the content of a Python script file resolved from the given path string.
     *
     * @param script the script object containing the path string for the script file, must be non-null
     * @return the script content as a {@link PythonScript}, never null but possibly empty
     * @throws PythonFileException if an I/O error occurs during reading
     */
    @Override
    public PythonScript readScript(PythonScript script) {
        if (!script.isFile()) {
            log.debug("Python script [name: {}] is not a file, returning as-is", script.getName());
            return script;
        }
        try {
            String source = script.getSource();
            log.debug("Reading Python script from source [length: {}]", source.length());
            String body = this.fileCache.computeIfAbsent(source, path -> {
                log.debug("Cache miss for Python script, loading from filesystem");
                try (InputStream inputStream = this.inputStreamProvider.open(path)) {
                    byte[] bytes = inputStream.readAllBytes();
                    log.debug("Successfully read [{}] bytes from Python script file", bytes.length);
                    return new String(bytes, this.charset);
                } catch (Exception e) {
                    log.error("Failed to read Python script file [path: {}]", path, e);
                    throw new PythonFileException(e);
                }
            });
            BasicPythonScriptBuilder.of(script).appendAll(body);
            log.debug("Python script loaded successfully [cache hit: {}]", this.fileCache.containsKey(source));
            return script;
        } catch (Exception e) {
            log.error("Failed to read Python script", e);
            throw new PythonFileException(e);
        }
    }
}