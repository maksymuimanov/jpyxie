package io.maksymuimanov.python.file;

import io.maksymuimanov.python.exception.PythonFileException;
import io.maksymuimanov.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the {@link PythonFileReader} interface providing
 * reading file operations for Python scripts.
 * <p>
 * This class supports reading from Python script files and creating script input
 * stream based on configured {@link BasicPythonFileReader#rootPath}.
 * </p>
 *
 * @see PythonFileReader
 * @author w4t3rcs
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class BasicPythonFileReader implements PythonFileReader {
    private final Map<String, String> fileCache;
    private final String rootPath;
    private final Charset charset;

    public BasicPythonFileReader(String rootPath, Charset charset) {
        this(new ConcurrentHashMap<>(), rootPath, charset);
    }

    /**
     * Reads the content of a Python script file resolved from the given path string.
     *
     * @param script the script object containing path string for the script file, must be non-null
     * @return the script content as a {@link PythonScript}, never null but possibly empty
     * @throws PythonFileException if an I/O error occurs during reading
     */
    @Override
    public PythonScript readScript(PythonScript script) {
        try {
            String source = script.getSource();
            String body = this.fileCache.computeIfAbsent(source, path -> {
                try (InputStream inputStream = this.getInputStream(path)) {
                    byte[] bytes = inputStream.readAllBytes();
                    return new String(bytes, this.charset);
                } catch (Exception e) {
                    throw new PythonFileException(e);
                }
            });
            script.getBuilder().appendAll(body);
            return script;
        } catch (Exception e) {
            throw new PythonFileException(e);
        }
    }

    @Override
    public InputStream getInputStream(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(this.rootPath + path);
            return resource.getInputStream();
        } catch (IOException e) {
            throw new PythonFileException(e);
        }
    }
}