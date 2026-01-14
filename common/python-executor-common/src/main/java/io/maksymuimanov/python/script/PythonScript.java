package io.maksymuimanov.python.script;

import io.maksymuimanov.python.exception.PythonScriptException;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PythonScript implements PythonRepresentation {
    public static final String FILE_FORMAT = ".py";
    public static final int START_INDEX = 0;
    @NonNull
    private final List<PythonImportLine> importLines;
    @NonNull
    private final List<PythonCodeLine> codeLines;
    private final boolean isFile;
    private final String name;
    private String body;

    public static PythonScript fromFile(@NonNull CharSequence name) {
        if (!isFile(name)) throw new PythonScriptException("Invalid file name format. It must end with " + FILE_FORMAT);
        return new PythonScript(new ArrayList<>(), new ArrayList<>(), true, name.toString());
    }

    public static boolean isFile(@NonNull CharSequence name) {
        return name.toString().endsWith(FILE_FORMAT);
    }

    public static PythonScript fromString(@NonNull CharSequence name, @NonNull CharSequence script) {
        PythonScript pythonScript = new PythonScript(new ArrayList<>(), new ArrayList<>(), false, name.toString());
        BasicPythonScriptBuilder.of(pythonScript).appendAll(script);
        return pythonScript;
    }

    public static PythonScript empty(@NonNull CharSequence name) {
        return new PythonScript(new ArrayList<>(), new ArrayList<>(), false, name.toString());
    }

    public PythonScript(@NonNull List<PythonImportLine> importLines, @NonNull List<PythonCodeLine> codeLines, boolean isFile, String name) {
        this.importLines = importLines;
        this.codeLines = codeLines;
        this.isFile = isFile;
        this.name = name;
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

    public boolean containsImport(CharSequence line) {
        PythonImportLine importLine = new PythonImportLine(line);
        return !this.isImportEmpty() && this.containsImport(importLine);
    }

    public boolean containsImport(PythonImportLine importLine) {
        return this.getImportLines().contains(importLine);
    }

    public boolean containsCode(CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return !this.isCodeEmpty() && this.getCodeLines().contains(codeLine);
    }

    public boolean containsDeepImport(CharSequence line) {
        return this.getImportLines()
                .stream()
                .anyMatch(importLine -> importLine.has(line));
    }

    public boolean containsDeepCode(CharSequence line) {
        return this.getCodeLines()
                .stream()
                .anyMatch(codeLine -> codeLine.has(line));
    }

    public boolean startsWithCode(CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.startsWithCode(codeLine);
    }

    public boolean startsWithCode(PythonCodeLine line) {
        return !this.isCodeEmpty() && this.getCodeLines().get(START_INDEX).equals(line);
    }

    public boolean endsWithCode(CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.endsWithCode(codeLine);
    }

    public boolean endsWithCode(PythonCodeLine line) {
        int lastElement = this.getCodeSize() - 1;
        return !this.isCodeEmpty() && this.getCodeLines().get(lastElement).equals(line);
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

    public PythonCodeLine getCode(int index) {
        return this.getCodeLines().get(index);
    }

    @NonNull
    public List<PythonImportLine> getImportLines() {
        return importLines;
    }

    @NonNull
    public List<PythonCodeLine> getCodeLines() {
        return codeLines;
    }

    public boolean isFile() {
        return isFile;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PythonScript that = (PythonScript) o;
        return this.isFile() == that.isFile()
                && Objects.equals(this.getImportLines(), that.getImportLines())
                && Objects.equals(this.getCodeLines(), that.getCodeLines())
                && Objects.equals(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImportLines(), getCodeLines(), isFile(), getName());
    }

    @Override
    public String toString() {
        return this.toPythonString();
    }

    @Override
    public String toPythonString() {
        if (this.body == null || this.body.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (PythonImportLine importLine : this.getImportLines()) {
                String importStatement = importLine.toPythonString();
                stringBuilder.append(importStatement).append("\n");
            }
            for (PythonCodeLine codeLine : this.getCodeLines()) {
                String codeStatement = codeLine.toPythonString();
                stringBuilder.append(codeStatement).append("\n");
            }

            this.body = stringBuilder.toString();
        }
        return this.body;
    }
}
