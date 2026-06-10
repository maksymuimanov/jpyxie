package io.jpyxie.python.script;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Data
@RequiredArgsConstructor
public class PythonScriptBuilder {
    private final PythonScript script;

    public PythonScriptBuilder computeAllImports(Predicate<PythonScriptLine> predicate, Function<PythonScriptLine, PythonScriptLine> function) {
        for (int i = 0; i < this.script.getImportsSize(); i++) {
            PythonScriptLine importLine = this.script.getImport(i);
            if (predicate.test(importLine)) {
                script.setImport(function.apply(importLine), i);
            }
        }
        return this;
    }

    public PythonScriptBuilder computeAllCode(Predicate<PythonScriptLine> predicate, Function<PythonScriptLine, PythonScriptLine> function) {
        for (int i = 0; i < this.script.getCodeSize(); i++) {
            PythonScriptLine codeLine = this.script.getCode(i);
            if (predicate.test(codeLine)) {
                script.setCode(function.apply(codeLine), i);
            }
        }
        return this;
    }

    public PythonScriptBuilder mapAllImports(Function<PythonScriptLine, PythonScriptLine> function) {
        List<PythonScriptLine> importLines = this.script.getImportLines();
        importLines.replaceAll(function::apply);
        return this;
    }

    public PythonScriptBuilder mapAllCode(Function<PythonScriptLine, PythonScriptLine> function) {
        List<PythonScriptLine> codeLines = this.script.getCodeLines();
        codeLines.replaceAll(function::apply);
        return this;
    }

    public PythonScriptBuilder appendImport(String line) {
        this.script.appendImport(line);
        return this;
    }

    public PythonScriptBuilder appendCode(String line) {
        this.script.appendCode(line);
        return this;
    }

    public PythonScriptBuilder prependImport(String line) {
        this.script.prependImport(line);
        return this;
    }

    public PythonScriptBuilder prependCode(String line) {
        this.script.prependCode(line);
        return this;
    }

    public PythonScriptBuilder insertImport(String line, int index) {
        this.script.insertImport(line, index);
        return this;
    }

    public PythonScriptBuilder insertCode(String line, int index) {
        this.script.insertCode(line, index);
        return this;
    }

    public PythonScriptBuilder setImport(String line, int index) {
        this.script.setImport(line, index);
        return this;
    }

    public PythonScriptBuilder setCode(String line, int index) {
        this.script.setCode(line, index);
        return this;
    }

    public PythonScriptBuilder removeImport(int index) {
        this.script.removeImport(index);
        return this;
    }

    public PythonScriptBuilder removeCode(int index) {
        this.script.removeCode(index);
        return this;
    }
}
