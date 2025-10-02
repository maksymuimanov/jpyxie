package io.w4t3rcs.python.script;

import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PythonScript {

    public static final String FILE_FORMAT = ".py";
    public static final int START_INDEX = 0;
    private final List<PythonImportLine> importLines;
    private final List<String> codeLines;
    private final boolean isFile;
    @Nullable
    private final String source;
    private String body;

    public PythonScript() {
        this.importLines = new ArrayList<>();
        this.codeLines = new ArrayList<>();
        this.isFile = false;
        this.source = null;
    }

    public PythonScript(String script) {
        this(new ArrayList<>(), new ArrayList<>(), script);
    }

    public PythonScript(List<PythonImportLine> importLines, List<String> codeLines, String script) {
        this.importLines = importLines;
        this.codeLines = codeLines;
        this.source = script;
        if (script.endsWith(FILE_FORMAT)) {
            this.isFile = true;
        } else {
            this.isFile = false;
            this.appendAll(script);
        }
    }

    public void appendAll(String script) {
        script.lines().forEach(line -> {
            if (line.matches(PythonImportLine.IMPORT_REGEX)) {
                this.appendImport(line);
            } else {
                this.appendCode(line);
            }
        });
    }

    public void removeAll(String regex, int start, int end, Consumer<String> actionOnRemove) {
        this.replaceAll(regex, start, end, group -> {
            actionOnRemove.accept(group);
            return "";
        });
    }

    public void replaceAll(String regex, int start, int end, Function<String, String> groupFunction) {
        this.replaceAll(regex,  matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            return groupFunction.apply(substring);
        });
    }

    public void replaceAll(String regex, int start, int end, BiConsumer<String, StringBuilder> resultBuilderFunction) {
        this.replaceAll(regex,  matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            StringBuilder result = new StringBuilder();
            resultBuilderFunction.accept(substring, result);
            return result.toString();
        });
    }

    public void replaceAll(String regex, Function<MatchResult, String> function) {
        this.body = null;
        Pattern pattern = Pattern.compile(regex);
        List<String> codeLines = this.getCodeLines();
        for (int i = 0; i < codeLines.size(); i++) {
            String codeLine = codeLines.get(i);
            Matcher matcher = pattern.matcher(codeLine);
            codeLine = matcher.replaceAll(function);
            this.setCode(codeLine, i);
        }
    }

    public void appendImport(String importLine) {
        this.body = null;
        PythonImportLine line = new PythonImportLine(importLine);
        if (importLines.contains(line)) return;
        this.getImportLines().add(line);
    }

    public List<String> getAllImportNames() {
        return this.getImportLines()
                .stream()
                .flatMap(pythonImportLine -> pythonImportLine.findNames().stream())
                .toList();
    }

    public void wrapCode(String left, String right) {
        this.prependCode(left);
        this.appendCode(right);
    }

    public void setCode(String codeLine, int index) {
        this.body = null;
        this.getCodeLines().set(index, codeLine);
    }

    public void insertCode(String codeLine, int index) {
        this.body = null;
        this.getCodeLines().add(index, codeLine);
    }

    public void appendCode(String... codeLines) {
        String joined = String.join("", codeLines);
        this.appendCode(joined);
    }

    public void appendCode(String codeLine) {
        this.body = null;
        this.getCodeLines().add(codeLine);
    }

    public void prependCode(String... codeLines) {
        String joined = String.join("", codeLines);
        this.prependCode(joined);
    }

    public void prependCode(String codeLine) {
        this.body = null;
        this.insertCode(codeLine, START_INDEX);
    }

    public boolean isEmpty() {
        return this.getCodeLines().isEmpty();
    }

    public boolean containsImport(String importLine) {
        PythonImportLine line = new PythonImportLine(importLine);
        return this.getImportLines().contains(line);
    }

    public boolean containsCode(String codeLine) {
        return this.getCodeLines().contains(codeLine);
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
        return this.getCodeLines().get(START_INDEX).equals(codeLine);
    }

    public int getImportIndex(String importLine) {
        PythonImportLine line = new PythonImportLine(importLine);
        return this.getImportLines().indexOf(line);
    }

    public int getCodeIndex(String codeLine) {
        return this.getCodeLines().indexOf(codeLine);
    }

    public List<PythonImportLine> getImportLines() {
        return importLines;
    }

    public List<String> getCodeLines() {
        return codeLines;
    }

    public boolean isFile() {
        return isFile;
    }

    public String getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PythonScript that)) return false;
        return Objects.equals(this.getImportLines(), that.getImportLines())
                && Objects.equals(this.getCodeLines(), that.getCodeLines())
                && Objects.equals(this.body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getImportLines(), this.getCodeLines(), this.body);
    }

    @Override
    public String toString() {
        if (this.body == null || this.body.isBlank()) {
            if (!this.startsWithCode("")) this.prependCode("");
            this.body = Stream.concat(this.getImportLines().stream().map(PythonImportLine::getLine), this.getCodeLines().stream())
                    .collect(Collectors.joining("\n"));
        }
        return this.body;
    }
}
