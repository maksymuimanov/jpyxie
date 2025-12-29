package io.maksymuimanov.python.script;

import io.maksymuimanov.python.util.CharSequenceUtils;
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
    private final CharSequence source;
    private CharSequence body;

    public PythonScript() {
        this.importLines = new ArrayList<>();
        this.codeLines = new ArrayList<>();
        this.isFile = false;
        this.source = null;
    }

    public PythonScript(CharSequence script) {
        this(new ArrayList<>(), new ArrayList<>(), script);
    }

    public PythonScript(@NonNull List<PythonImportLine> importLines, @NonNull List<PythonCodeLine> codeLines, CharSequence script) {
        this.importLines = importLines;
        this.codeLines = codeLines;
        this.source = script;
        this.body = null;
        if (CharSequenceUtils.endsWith(script, FILE_FORMAT)) {
            this.isFile = true;
        } else {
            this.isFile = false;
            BasicPythonScriptBuilder.of(this).appendAll(script);
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

    public CharSequence getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PythonScript that = (PythonScript) o;
        return isFile() == that.isFile()
                && Objects.equals(getImportLines(), that.getImportLines())
                && Objects.equals(getCodeLines(), that.getCodeLines())
                && Objects.equals(getSource().toString(), that.getSource().toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImportLines(), getCodeLines(), isFile(), getSource());
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
        return this.body.toString();
    }
}
