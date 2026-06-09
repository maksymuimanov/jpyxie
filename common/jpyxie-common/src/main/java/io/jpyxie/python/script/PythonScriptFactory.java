package io.jpyxie.python.script;

import io.jpyxie.python.PythonConstants;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@UtilityClass
public class PythonScriptFactory {
    private static final PythonScript EMPTY = PythonScriptFactory.empty("empty");

    public static FilePythonScript fromFile(String name, File file) {
        return fromFile(name, file, StandardCharsets.UTF_8);
    }

    public static FilePythonScript fromFile(String name, File file, Charset charset) {
        Path path = file.toPath();
        return fromPath(name, path, charset);
    }

    public static FilePythonScript fromPath(String name, Path path) {
        return fromPath(name, path, StandardCharsets.UTF_8);
    }

    public static FilePythonScript fromPath(String name, Path path, Charset charset) {
        try (Stream<String> lines = Files.lines(path, charset)) {
            FilePythonScript script = new FilePythonScript(name, path);
            lines.forEach(line -> {
                PythonScriptLine scriptLine = new PythonScriptLine(line);
                if (line.matches(PythonConstants.IMPORT_REGEX)) {
                    script.getImportLines().add(scriptLine);
                } else {
                    script.getCodeLines().add(scriptLine);
                }
            });
            return script;
        } catch (IOException e) {
            throw PythonScriptException.readingFailure(e);
        }
    }

    public static PythonScript fromInputStream(String name, InputStream inputStream, Charset charset) {
        try {
            byte[] bytes = inputStream.readAllBytes();
            String scriptString = new String(bytes, charset);
            return fromCharSequence(name, scriptString);
        } catch (IOException e) {
            throw PythonScriptException.readingFailure(e);
        }
    }

    public static PythonScript fromCharSequence(String name, CharSequence charSequence) {
        String scriptString = charSequence.toString();
        return PythonScriptFactory.fromStream(name, scriptString.lines());
    }

    public static PythonScript empty() {
        return EMPTY;
    }

    public static PythonScript empty(String name) {
        return new PythonScript(name, List.of(), List.of());
    }

    public static PythonScript fromInputStream(String name, InputStream inputStream) {
        return fromReader(name, new InputStreamReader(inputStream));
    }

    public static PythonScript fromReader(String name, Reader reader) {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            return fromStream(name, bufferedReader.lines());
        } catch (IOException e) {
            throw PythonScriptException.readingFailure(e);
        }
    }

    public static PythonScript fromStream(String name, Stream<String> lines) {
        PythonScript script = new PythonScript(name);
        lines.forEach(line -> {
            PythonScriptLine scriptLine = new PythonScriptLine(line);
            if (line.matches(PythonConstants.IMPORT_REGEX)) {
                script.getImportLines().add(scriptLine);
            } else {
                script.getCodeLines().add(scriptLine);
            }
        });
        return script;
    }
}
