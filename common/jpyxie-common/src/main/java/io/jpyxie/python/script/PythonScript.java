package io.jpyxie.python.script;

import io.jpyxie.python.constant.PythonConstants;
import io.jpyxie.python.exception.PythonScriptException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record PythonScript(String name, String source, boolean isFile, List<PythonImportLine> importLines,
                           List<PythonCodeLine> codeLines) implements PythonRepresentation {
    public static final int START_INDEX = 0;

    public static PythonScript parse(CharSequence name, CharSequence script) {
        String scriptString = script.toString();
        return isFile(scriptString) ? asFile(name, scriptString) : asString(name, scriptString);
    }

    public static PythonScript asFile(CharSequence name) {
        String nameString = name.toString();
        return asFile(nameString, isFile(nameString) ? nameString : nameString + PythonConstants.FILE_FORMAT);
    }

    public static PythonScript asFile(CharSequence name, CharSequence script) {
        if (!isFile(script))
            throw new PythonScriptException("Invalid file name format. It must end with " + PythonConstants.FILE_FORMAT);
        return new PythonScript(name.toString(), script.toString(), true, new ArrayList<>(), new ArrayList<>());
    }

    public static boolean isFile(CharSequence source) {
        return source.toString().endsWith(PythonConstants.FILE_FORMAT);
    }

    public static PythonScript asString(CharSequence name, CharSequence script) {
        PythonScript pythonScript = new PythonScript(name.toString(), script.toString(), false, new ArrayList<>(), new ArrayList<>());
        BasicPythonScriptBuilder.of(pythonScript).appendAll(script);
        return pythonScript;
    }

    public static PythonScript empty(CharSequence name) {
        return new PythonScript(name.toString(), "", false, new ArrayList<>(), new ArrayList<>());
    }

    public int getImportsSize() {
        return this.importLines().size();
    }

    public int getCodeSize() {
        return this.codeLines().size();
    }

    public boolean containsImport(CharSequence line) {
        PythonImportLine importLine = new PythonImportLine(line);
        return !this.isImportEmpty() && this.containsImport(importLine);
    }

    public boolean containsImport(PythonImportLine importLine) {
        return this.importLines().contains(importLine);
    }

    public boolean containsCode(CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return !this.isCodeEmpty() && this.codeLines().contains(codeLine);
    }

    public boolean containsDeepImport(CharSequence line) {
        return this.importLines()
                .stream()
                .anyMatch(importLine -> importLine.has(line));
    }

    public boolean containsDeepCode(CharSequence line) {
        return this.codeLines()
                .stream()
                .anyMatch(codeLine -> codeLine.has(line));
    }

    public boolean startsWithCode(CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.startsWithCode(codeLine);
    }

    public boolean startsWithCode(PythonCodeLine line) {
        return !this.isCodeEmpty() && this.codeLines().get(START_INDEX).equals(line);
    }

    public boolean endsWithCode(CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.endsWithCode(codeLine);
    }

    public boolean endsWithCode(PythonCodeLine line) {
        int lastElement = this.getCodeSize() - 1;
        return !this.isCodeEmpty() && this.codeLines().get(lastElement).equals(line);
    }

    public boolean isImportEmpty() {
        return this.importLines().isEmpty();
    }

    public boolean isCodeEmpty() {
        return this.codeLines().isEmpty();
    }

    public PythonImportLine getImport(int index) {
        return this.importLines().get(index);
    }

    public PythonCodeLine getCode(int index) {
        return this.codeLines().get(index);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PythonScript that = (PythonScript) object;
        return this.isFile() == that.isFile()
                && Objects.equals(this.name(), that.name())
                && Objects.equals(this.source(), that.source())
                && Objects.equals(this.importLines(), that.importLines())
                && Objects.equals(this.codeLines(), that.codeLines());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name(), this.source(), this.isFile(), this.importLines(), this.codeLines());
    }

    @Override
    public String toString() {
        return this.toPythonString();
    }

    @Override
    public String toPythonString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PythonImportLine importLine : this.importLines()) {
            String importStatement = importLine.toPythonString();
            stringBuilder.append(importStatement)
                    .append("\n");
        }
        for (PythonCodeLine codeLine : this.codeLines()) {
            String codeStatement = codeLine.toPythonString();
            stringBuilder.append(codeStatement)
                    .append("\n");
        }

        return stringBuilder.toString();
    }
}
