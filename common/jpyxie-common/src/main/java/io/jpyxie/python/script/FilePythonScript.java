package io.jpyxie.python.script;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.file.Path;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FilePythonScript extends PythonScript {
    private final Path path;

    public FilePythonScript(String name, Path path) {
        super(name);
        this.path = path;
    }

    public FilePythonScript(String name, List<PythonScriptLine> importLines, List<PythonScriptLine> codeLines, Path path) {
        super(name, importLines, codeLines);
        this.path = path;
    }
}
