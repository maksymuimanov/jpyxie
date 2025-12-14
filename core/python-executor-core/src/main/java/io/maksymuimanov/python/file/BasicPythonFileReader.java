package io.maksymuimanov.python.file;

import io.maksymuimanov.python.exception.PythonFileException;
import io.maksymuimanov.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link PythonFileReader} interface providing
 * reading file operations for Python scripts.
 * <p>
 * This class supports reading from Python script files and resolving script paths
 * based on configured {@link BasicPythonFileReader#rootPath}.
 * </p>
 * <p>
 * The class assumes script files are encoded in the platform default charset.
 * The returned script content preserves line breaks as newline characters.
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

    public BasicPythonFileReader(String rootPath) {
        this(new ConcurrentHashMap<>(), rootPath);
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
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getInputStream(path)))) {
                    return bufferedReader.lines().collect(Collectors.joining("\n"));
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

    /**
     * Resolves the {@link InputStream} for the script file by appending the given relative path
     * to the base path configured in {@link BasicPythonFileReader#rootPath}.
     *
     * @param path the relative path string of the script file, must be non-null
     * @return the resolved {@link InputStream} to the script file
     * @throws PythonFileException if the resource cannot be resolved
     */
    @Override
    public InputStream getInputStream(String path) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(this.rootPath + path);
            return classPathResource.getInputStream();
        } catch (IOException e) {
            throw new PythonFileException(e);
        }
    }
}