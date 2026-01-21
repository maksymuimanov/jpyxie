package io.maksymuimanov.python.script;

import io.maksymuimanov.python.exception.PythonScriptException;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PythonScript implements PythonRepresentation {
    public static final String FILE_FORMAT = ".py";
    public static final int START_INDEX = 0;
    private final String name;
    private final String source;
    private final boolean isFile;
    private final List<PythonImportLine> importLines;
    private final List<PythonCodeLine> codeLines;
    @Nullable
    private String body;

    public static PythonScript parse(CharSequence name, CharSequence script) {
        String scriptString = script.toString();
        return isFile(scriptString) ? fromFile(name, scriptString) : fromString(name, scriptString);
    }

    public static PythonScript fromFile(CharSequence name) {
        String nameString = name.toString();
        return fromFile(nameString, isFile(nameString) ? nameString : nameString + FILE_FORMAT);
    }

    public static PythonScript fromFile(CharSequence name, CharSequence script) {
        if (!isFile(script)) throw new PythonScriptException("Invalid file name format. It must end with " + FILE_FORMAT);
        return new PythonScript(name.toString(), script.toString(), true, new ArrayList<>(), new ArrayList<>());
    }

    public static boolean isFile(CharSequence source) {
        return source.toString().endsWith(FILE_FORMAT);
    }

    public static PythonScript fromString(CharSequence name, CharSequence script) {
        PythonScript pythonScript = new PythonScript(name.toString(), script.toString(), false, new ArrayList<>(), new ArrayList<>());
        BasicPythonScriptBuilder.of(pythonScript).appendAll(script);
        return pythonScript;
    }

    public static PythonScript empty(CharSequence name) {
        return new PythonScript(name.toString(), "", false, new ArrayList<>(), new ArrayList<>());
    }

    public PythonScript(String name, String source, boolean isFile, List<PythonImportLine> importLines, List<PythonCodeLine> codeLines) {
        this.name = name;
        this.source = source;
        this.isFile = isFile;
        this.importLines = importLines;
        this.codeLines = codeLines;
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

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public boolean isFile() {
        return isFile;
    }

    public List<PythonImportLine> getImportLines() {
        return importLines;
    }

    public List<PythonCodeLine> getCodeLines() {
        return codeLines;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PythonScript that = (PythonScript) object;
        return this.isFile() == that.isFile()
                && Objects.equals(this.getName(), that.getName())
                && Objects.equals(this.getSource(), that.getSource())
                && Objects.equals(this.getImportLines(), that.getImportLines())
                && Objects.equals(this.getCodeLines(), that.getCodeLines());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getSource(), this.isFile(), this.getImportLines(), this.getCodeLines());
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
