package io.jpyxie.python.script;

import io.jpyxie.python.PythonRepresentation;
import io.jpyxie.python.PythonConstants;
import io.jpyxie.python.exception.PythonScriptException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class PythonScript implements PythonRepresentation {
    private static final PythonScript EMPTY = PythonScript.empty("empty");
    protected static final int START_INDEX = 0;
    @Serial
    private static final long serialVersionUID = 0L;
    private final String name;
    private final List<PythonScriptLine> importLines;
    private final List<PythonScriptLine> codeLines;

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
            throw PythonScriptException.inputStreamException(e);
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

    public PythonScript(String name) {
        this(name, new ArrayList<>(), new ArrayList<>());
    }

    public void appendImport(CharSequence line) {
        this.appendImport(new PythonScriptLine(line));
    }

    public void appendImport(PythonScriptLine line) {
        this.getImportLines().add(line);
    }

    public void appendCode(CharSequence line) {
        this.appendCode(new PythonScriptLine(line));
    }

    public void appendCode(PythonScriptLine line) {
        this.getCodeLines().add(line);
    }

    public void prependImport(CharSequence line) {
        this.prependImport(new PythonScriptLine(line));
    }

    public void prependImport(PythonScriptLine line) {
        this.insertImport(line, START_INDEX);
    }

    public void prependCode(CharSequence line) {
        this.prependCode(new PythonScriptLine(line));
    }

    public void prependCode(PythonScriptLine line) {
        this.insertCode(line, START_INDEX);
    }

    public void insertImport(CharSequence line, int index) {
        this.insertImport(new PythonScriptLine(line), index);
    }

    public void insertImport(PythonScriptLine line, int index) {
        this.getImportLines().add(index, line);
    }

    public void insertCode(CharSequence line, int index) {
        this.insertCode(new PythonScriptLine(line), index);
    }

    public void insertCode(PythonScriptLine line, int index) {
        this.getCodeLines().add(index, line);
    }

    public void setImport(CharSequence line, int index) {
        this.setImport(new PythonScriptLine(line), index);
    }

    public void setImport(PythonScriptLine line, int index) {
        this.getImportLines().set(index, line);
    }

    public void setCode(CharSequence line, int index) {
        this.setCode(new PythonScriptLine(line), index);
    }

    public void setCode(PythonScriptLine line, int index) {
        this.getCodeLines().set(index, line);
    }

    public void removeImport(int index) {
        this.getImportLines().remove(index);
    }

    public void removeCode(int index) {
        this.getCodeLines().remove(index);
    }

    public PythonScriptLine getImport(int index) {
        return this.getImportLines().get(index);
    }

    public PythonScriptLine getCode(int index) {
        return this.getCodeLines().get(index);
    }

    public int getImportsSize() {
        return this.getImportLines().size();
    }

    public int getCodeSize() {
        return this.getCodeLines().size();
    }

    public boolean containsImport(CharSequence line) {
        return this.containsImport(new PythonScriptLine(line));
    }

    public boolean containsImport(PythonScriptLine line) {
        return this.hasImports() && this.getImportLines().contains(line);
    }

    public boolean containsCode(CharSequence line) {
        return this.containsCode(new PythonScriptLine(line));
    }

    public boolean containsCode(PythonScriptLine line) {
        return this.hasCode() && this.getCodeLines().contains(line);
    }

    public boolean containsDeepImport(CharSequence line) {
        return this.getImportLines()
                .stream()
                .anyMatch(importLine -> importLine.contains(line));
    }

    public boolean containsDeepCode(CharSequence line) {
        return this.getCodeLines()
                .stream()
                .anyMatch(codeLine -> codeLine.contains(line));
    }

    public boolean startsWithImport(CharSequence line) {
        return this.startsWithImport(new PythonScriptLine(line));
    }

    public boolean startsWithImport(PythonScriptLine line) {
        return this.hasImports() && this.getImportLines().get(START_INDEX).equals(line);
    }

    public boolean startsWithCode(CharSequence line) {
        return this.startsWithCode(new PythonScriptLine(line));
    }

    public boolean startsWithCode(PythonScriptLine line) {
        return this.hasCode() && this.getCodeLines().get(START_INDEX).equals(line);
    }

    public boolean endsWithImport(CharSequence line) {
        return this.endsWithImport(new PythonScriptLine(line));
    }

    public boolean endsWithImport(PythonScriptLine line) {
        int lastElement = this.getImportsSize() - 1;
        return this.hasImports() && this.getImportLines().get(lastElement).equals(line);
    }

    public boolean endsWithCode(CharSequence line) {
        return this.endsWithCode(new PythonScriptLine(line));
    }

    public boolean endsWithCode(PythonScriptLine line) {
        int lastElement = this.getCodeSize() - 1;
        return this.hasCode() && this.getCodeLines().get(lastElement).equals(line);
    }

    public boolean hasImports() {
        return !this.hasNoImports();
    }

    public boolean hasNoImports() {
        return this.getImportLines().isEmpty();
    }

    public boolean hasCode() {
        return !this.hasNoCode();
    }

    public boolean hasNoCode() {
        return this.getCodeLines().isEmpty();
    }

    @Override
    public String toString() {
        return this.toPythonString();
    }

    @Override
    public String toPythonString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PythonScriptLine importLine : this.getImportLines()) {
            String importStatement = importLine.toPythonString();
            stringBuilder.append(importStatement)
                    .append("\n");
        }
        for (PythonScriptLine codeLine : this.getCodeLines()) {
            String codeStatement = codeLine.toPythonString();
            stringBuilder.append(codeStatement)
                    .append("\n");
        }

        return stringBuilder.toString();
    }
}