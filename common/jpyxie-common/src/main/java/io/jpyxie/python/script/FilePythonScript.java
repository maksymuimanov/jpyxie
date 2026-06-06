package io.jpyxie.python.script;

import io.jpyxie.python.PythonConstants;
import io.jpyxie.python.exception.PythonScriptException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(callSuper = true)
public class FilePythonScript extends PythonScript {
    private final Path path;

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
            throw PythonScriptException.inputStreamException(e);
        }
    }

    public FilePythonScript(String name, Path path) {
        super(name);
        this.path = path;
    }

    public FilePythonScript(String name, List<PythonScriptLine> importLines, List<PythonScriptLine> codeLines, Path path) {
        super(name, importLines, codeLines);
        this.path = path;
    }
}
