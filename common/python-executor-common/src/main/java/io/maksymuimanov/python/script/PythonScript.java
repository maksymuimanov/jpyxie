package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PythonScript implements PythonRepresentation {
    public static final String FILE_FORMAT = ".py";
    public static final int START_INDEX = 0;
    @NonNull
    private final PythonScriptBuilder builder;
    @NonNull
    private final List<PythonImportLine> importLines;
    @NonNull
    private final List<String> codeLines;
    private final boolean isFile;
    private final String source;
    private String body;

    public PythonScript() {
        this.builder = new PythonScriptBuilder(this);
        this.importLines = new ArrayList<>();
        this.codeLines = new ArrayList<>();
        this.isFile = false;
        this.source = null;
    }

    public PythonScript(String script) {
        this(new ArrayList<>(), new ArrayList<>(), script);
    }

    public PythonScript(@NonNull List<PythonImportLine> importLines, @NonNull List<String> codeLines, String script) {
        this.builder = new PythonScriptBuilder(this);
        this.importLines = importLines;
        this.codeLines = codeLines;
        this.source = script;
        this.body = null;
        if (script.endsWith(FILE_FORMAT)) {
            this.isFile = true;
        } else {
            this.isFile = false;
            this.builder.appendAll(script);
        }
    }

    public void clearBody() {
        this.body = null;
    }

    public int getImportsSize() {
        return this.getImportLines().size();
    }

    public int getCodeSize() {
        return this.getCodeLines().size();
    }

    public boolean containsImport(String importLine) {
        PythonImportLine line = new PythonImportLine(importLine);
        return this.containsImport(line);
    }

    public boolean containsImport(PythonImportLine importLine) {
        return this.getImportLines().contains(importLine);
    }

    public boolean containsCode(String codeLine) {
        return !this.isCodeEmpty() && this.getCodeLines().contains(codeLine);
    }

    public boolean containsDeepImport(String importLine) {
        return this.getImportLines()
                .stream()
                .anyMatch(line -> line.getLine().contains(importLine));
    }

    public boolean containsDeepCode(String codeLine) {
        return this.getCodeLines()
                .stream()
                .anyMatch(line -> line.contains(codeLine));
    }

    public boolean startsWithCode(String codeLine) {
        return !this.isCodeEmpty() && this.getCodeLines().get(START_INDEX).equals(codeLine);
    }

    public boolean endsWithCode(String codeLine) {
        int lastElement = this.getCodeSize() - 1;
        return !this.isCodeEmpty() && this.getCodeLines().get(lastElement).equals(codeLine);
    }

    public boolean isImportEmpty() {
        return this.getImportLines().isEmpty();
    }

    public boolean isCodeEmpty() {
        return this.getCodeLines().isEmpty();
    }

    public PythonImportLine getImport(int index) {
        return this.getImportLines().get(index);
    }

    public String getCodeLine(int index) {
        return this.getCodeLines().get(index);
    }

    public int getImportIndex(String importLine) {
        PythonImportLine line = new PythonImportLine(importLine);
        return this.getImportLines().indexOf(line);
    }

    public int getCodeIndex(String codeLine) {
        return this.getCodeLines().indexOf(codeLine);
    }

    public List<String> getAllImportNames() {
        return this.getImportLines()
                .stream()
                .flatMap(pythonImportLine -> pythonImportLine.findNames().stream())
                .toList();
    }

    @NonNull
    public List<PythonImportLine> getImportLines() {
        return importLines;
    }

    @NonNull
    public List<String> getCodeLines() {
        return codeLines;
    }

    public boolean isFile() {
        return isFile;
    }

    public String getSource() {
        return source;
    }

    @NonNull
    public PythonScriptBuilder getBuilder() {
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PythonScript that)) return false;
        return Objects.equals(this.getImportLines(), that.getImportLines())
                && Objects.equals(this.getCodeLines(), that.getCodeLines());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getImportLines(), this.getCodeLines());
    }

    @Override
    public String toString() {
        return this.toPythonString();
    }

    @Override
    public String toPythonString() {
        if (this.body == null || this.body.isBlank()) {
            this.body = Stream.concat(this.getImportLines().stream().map(PythonImportLine::getLine), this.getCodeLines().stream())
                    .collect(Collectors.joining("\n"));
        }
        return this.body;
    }
}
