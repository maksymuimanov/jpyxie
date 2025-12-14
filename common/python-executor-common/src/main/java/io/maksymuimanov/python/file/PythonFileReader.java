package io.maksymuimanov.python.file;

import io.maksymuimanov.python.script.PythonScript;

import java.io.InputStream;

/**
 * Defines reading operations for working with Python script files, including validation, I/O,
 * and content transformation.
 *
 * <p>File format is assumed to use the {@link PythonScript#FILE_FORMAT} extension ({@code ".py"}).
 * Implementations must handle file system I/O in a defined, consistent manner and
 * should ensure proper resource management (e.g., closing streams, handling encoding).
 * Unless explicitly documented, all methods are expected to be thread-safe.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * PythonFileReader reader = ...;
 * PythonScript script = reader.readScript("script.py");
 * }</pre>
 *
 * @author w4t3rcs
 * @since 1.0.0
 */
public interface PythonFileReader {
    /**
     * Reads the full content of a Python script from a file.
     *
     * @param pythonScript non-{@code null} file system path container as a {@link PythonScript}
     * @return non-{@code null} script content
     */
    PythonScript readScript(PythonScript pythonScript);

    /**
     * Returns an {@link InputStream} object representing the input stream of the given script.
     *
     * @param path non-{@code null} file system path
     * @return non-{@code null} {@link InputStream} instance pointing to the script
     */
    InputStream getInputStream(String path);
}