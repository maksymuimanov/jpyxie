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

    public <T> PythonScript iterate(Iterable<T> iterable, Consumer<T> action) {
        return this.iterate(iterable, action, true);
    }

    public <T> PythonScript iterate(Iterable<T> iterable, Consumer<T> action, boolean condition) {
        if (condition) iterable.forEach(action);
        return this;
    }

    public PythonScript perform(Runnable action) {
        return this.perform(action, true);
    }

    public PythonScript perform(Runnable action, boolean condition) {
        if (condition) action.run();
        return this;
    }

    public PythonScript appendAll(String script) {
        script.lines().forEach(line -> {
            if (line.matches(PythonImportLine.IMPORT_REGEX)) {
                this.appendImport(line);
            } else {
                this.appendCode(line);
            }
        });
        return this;
    }

    public PythonScript removeAll(String regex, int start, int end, Consumer<String> actionOnRemove) {
        this.replaceAll(regex, start, end, group -> {
            actionOnRemove.accept(group);
            return "";
        });
        return this;
    }

    public PythonScript replaceAll(String regex, int start, int end, Function<String, String> groupFunction) {
        this.replaceAll(regex,  matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            return groupFunction.apply(substring);
        });
        return this;
    }

    public PythonScript replaceAll(String regex, int start, int end, BiConsumer<String, StringBuilder> resultBuilderFunction) {
        this.replaceAll(regex,  matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            StringBuilder result = new StringBuilder();
            resultBuilderFunction.accept(substring, result);
            return result.toString();
        });
        return this;
    }

    public PythonScript replaceAll(String regex, Function<MatchResult, String> function) {
        this.body = null;
        Pattern pattern = Pattern.compile(regex);
        List<String> codeLines = this.getCodeLines();
        for (int i = 0; i < codeLines.size(); i++) {
            String codeLine = codeLines.get(i);
            Matcher matcher = pattern.matcher(codeLine);
            codeLine = matcher.replaceAll(function);
            this.setCode(codeLine, i);
        }
        return this;
    }

    public PythonScript appendImport(String importLine) {
        this.body = null;
        PythonImportLine line = new PythonImportLine(importLine);
        if (importLines.contains(line)) return this;
        this.getImportLines().add(line);
        return this;
    }

    public PythonScript setCode(String codeLine, int index) {
        this.body = null;
        this.getCodeLines().set(index, codeLine);
        return this;
    }

    public PythonScript insertCode(String codeLine, int index) {
        this.body = null;
        this.getCodeLines().add(index, codeLine);
        return this;
    }

    public PythonScript appendCode(String... codeLines) {
        String joined = String.join("", codeLines);
        this.appendCode(joined);
        return this;
    }

    public PythonScript appendCode(String codeLine) {
        this.body = null;
        this.getCodeLines().add(codeLine);
        return this;
    }

    public PythonScript prependCode(String... codeLines) {
        String joined = String.join("", codeLines);
        this.prependCode(joined);
        return this;
    }

    public PythonScript prependCode(String codeLine) {
        this.body = null;
        this.insertCode(codeLine, START_INDEX);
        return this;
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

    public List<String> getAllImportNames() {
        return this.getImportLines()
                .stream()
                .flatMap(pythonImportLine -> pythonImportLine.findNames().stream())
                .toList();
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
