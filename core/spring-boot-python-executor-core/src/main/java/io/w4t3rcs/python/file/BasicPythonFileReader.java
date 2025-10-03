package io.w4t3rcs.python.file;

import io.w4t3rcs.python.exception.PythonScriptPathGettingException;
import io.w4t3rcs.python.exception.PythonScriptReadingFromFileException;
import io.w4t3rcs.python.properties.PythonFileProperties;
import io.w4t3rcs.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link PythonFileReader} interface providing
 * reading file operations for Python scripts.
 * <p>
 * This class supports reading from Python script files and resolving script paths
 * based on configured {@link PythonFileProperties}.
 * </p>
 * <p>
 * The class assumes script files are encoded in the platform default charset.
 * The returned script content preserves line breaks as newline characters.
 * </p>
 *
 * @see PythonFileReader
 * @see PythonFileProperties
 * @author w4t3rcs
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class BasicPythonFileReader implements PythonFileReader {
    private final PythonFileProperties fileProperties;

    /**
     * Reads the content of a Python script file resolved from the given path string.
     *
     * @param script the script object containing path string for the script file, must be non-null
     * @return the script content as a {@link PythonScript}, never null but possibly empty
     * @throws PythonScriptReadingFromFileException if an I/O error occurs during reading
     */
    @Override
    public PythonScript readScript(PythonScript script) {
        String source = script.getSource();
        Path scriptPath = this.getScriptPath(source);
        try (BufferedReader bufferedReader = Files.newBufferedReader(scriptPath)) {
            String body = bufferedReader.lines().collect(Collectors.joining("\n"));
            script.getBuilder().appendAll(body);
            return script;
        } catch (IOException e) {
            throw new PythonScriptReadingFromFileException(e);
        }
    }

    /**
     * Resolves the {@link Path} for the script file by appending the given relative path
     * to the base path configured in {@link PythonFileProperties}.
     *
     * @param path the relative path string of the script file, must be non-null
     * @return the resolved absolute {@link Path} to the script file
     * @throws PythonScriptPathGettingException if the resource cannot be resolved as a file path
     */
    @Override
    public Path getScriptPath(String path) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(fileProperties.path() + path);
            return classPathResource.getFile().toPath();
        } catch (IOException e) {
            throw new PythonScriptPathGettingException(e);
        }
    }
}