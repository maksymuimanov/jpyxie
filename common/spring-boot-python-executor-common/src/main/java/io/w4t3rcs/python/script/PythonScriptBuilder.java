package io.w4t3rcs.python.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PythonScriptBuilder {
    private final PythonScript script;

    public PythonScriptBuilder(PythonScript script) {
        this.script = script;
    }

    public PythonScriptBuilder removeAllNullImports() {
        while (this.script.containsImport((PythonImportLine) null)) {
            this.removeImport((PythonImportLine) null);
        }
        return this;
    }

    public PythonScriptBuilder removeAllNullCode() {
        while (this.script.containsCode(null)) {
            this.removeCode(null);
        }
        return this;
    }

    public PythonScriptBuilder iterateImportLines(Consumer<PythonImportLine> action) {
        return this.iterateImportLines(action, true);
    }

    public PythonScriptBuilder iterateImportLines(Consumer<PythonImportLine> action, boolean condition) {
        List<PythonImportLine> importLinesCopy = new ArrayList<>(this.script.getImportLines());
        return this.iterate(importLinesCopy, action, condition);
    }

    public PythonScriptBuilder iterateImportLines(BiConsumer<PythonImportLine, Integer> action) {
        return this.iterateImportLines(action, true);
    }

    public PythonScriptBuilder iterateImportLines(BiConsumer<PythonImportLine, Integer> action, boolean condition) {
        List<PythonImportLine> importLinesCopy = new ArrayList<>(this.script.getImportLines());
        return this.iterate(importLinesCopy, action, condition);
    }

    public PythonScriptBuilder iterateCodeLines(Consumer<String> action) {
        return this.iterateCodeLines(action, true);
    }

    public PythonScriptBuilder iterateCodeLines(Consumer<String> action, boolean condition) {
        List<String> codeLinesCopy = new ArrayList<>(this.script.getCodeLines());
        return this.iterate(codeLinesCopy, action, condition);
    }

    public PythonScriptBuilder iterateCodeLines(BiConsumer<String, Integer> action) {
        return this.iterateCodeLines(action, true);
    }

    public PythonScriptBuilder iterateCodeLines(BiConsumer<String, Integer> action, boolean condition) {
        List<String> codeLinesCopy = new ArrayList<>(this.script.getCodeLines());
        return this.iterate(codeLinesCopy, action, condition);
    }

    public <T> PythonScriptBuilder iterate(Iterable<T> iterable, Consumer<T> action) {
        return this.iterate(iterable, action, true);
    }

    public <T> PythonScriptBuilder iterate(Iterable<T> iterable, Consumer<T> action, boolean condition) {
        this.script.clearBody();
        if (condition) iterable.forEach(action);
        return this;
    }

    public <T> PythonScriptBuilder iterate(List<T> iterable, BiConsumer<T, Integer> action) {
        return this.iterate(iterable, action, true);
    }

    public <T> PythonScriptBuilder iterate(List<T> iterable, BiConsumer<T, Integer> action, boolean condition) {
        this.script.clearBody();
        if (condition) {
            for (int i = 0; i < iterable.size(); i++) {
                T element = iterable.get(i);
                action.accept(element, i);
            }
        }

        return this;
    }

    public PythonScriptBuilder doOnCondition(Runnable action, boolean condition) {
        this.script.clearBody();
        if (condition) action.run();
        return this;
    }

    public PythonScriptBuilder appendAll(String script) {
        this.script.clearBody();
        script.lines().forEach(line -> {
            if (line.matches(PythonImportLine.IMPORT_REGEX)) {
                this.appendImport(line);
            } else {
                this.appendCode(line);
            }
        });
        return this;
    }

    public PythonScriptBuilder appendImport(String importLine) {
        PythonImportLine line = new PythonImportLine(importLine);
        return this.appendImport(line);
    }

    public PythonScriptBuilder appendImport(PythonImportLine importLine) {
        this.script.clearBody();
        if (this.script.containsImport(importLine)) return this;
        this.script.getImportLines().add(importLine);
        return this;
    }

    public PythonScriptBuilder prependImport(String importLine) {
        PythonImportLine line = new PythonImportLine(importLine);
        return this.prependImport(line);
    }

    public PythonScriptBuilder prependImport(PythonImportLine importLine) {
        this.script.clearBody();
        if (this.script.containsImport(importLine)) return this;
        this.script.getImportLines().add(PythonScript.START_INDEX, importLine);
        return this;
    }

    public PythonScriptBuilder setImport(String importLine, int index) {
        PythonImportLine line = new PythonImportLine(importLine);
        return this.setImport(line, index);
    }

    public PythonScriptBuilder setImport(PythonImportLine importLine, int index) {
        this.script.clearBody();
        this.script.getImportLines().set(index, importLine);
        return this;
    }

    public PythonScriptBuilder removeImport(int index) {
        this.script.clearBody();
        this.script.getImportLines().remove(index);
        return this;
    }

    public PythonScriptBuilder removeImport(String importLine) {
        PythonImportLine line = new PythonImportLine(importLine);
        return this.removeImport(line);
    }

    public PythonScriptBuilder removeImport(PythonImportLine importLine) {
        this.script.clearBody();
        this.script.getImportLines().remove(importLine);
        return this;
    }

    public PythonScriptBuilder appendCode(String... codeLineElements) {
        String joined = String.join("", codeLineElements);
        return this.appendCode(joined);
    }

    public PythonScriptBuilder appendCode(String codeLine) {
        this.script.clearBody();
        this.script.getCodeLines().add(codeLine);
        return this;
    }

    public PythonScriptBuilder prependCode(String... codeLineElements) {
        String joined = String.join("", codeLineElements);
        return this.prependCode(joined);
    }

    public PythonScriptBuilder prependCode(String codeLine) {
        this.script.clearBody();
        return this.insertCode(codeLine, PythonScript.START_INDEX);
    }

    public PythonScriptBuilder insertCode(String codeLine, int index) {
        this.script.clearBody();
        this.script.getCodeLines().add(index, codeLine);
        return this;
    }

    public PythonScriptBuilder setCode(String codeLine, int index) {
        this.script.clearBody();
        this.script.getCodeLines().set(index, codeLine);
        return this;
    }

    public PythonScriptBuilder removeAllDeepCode(String regex, int start, int end, Consumer<String> actionOnRemove) {
        return this.replaceAllCode(regex, start, end, group -> {
            actionOnRemove.accept(group);
            return "";
        });
    }

    public PythonScriptBuilder removeCode(int index) {
        this.script.clearBody();
        this.script.getCodeLines().remove(index);
        return this;
    }

    public PythonScriptBuilder removeCode(String codeLine) {
        this.script.clearBody();
        this.script.getCodeLines().remove(codeLine);
        return this;
    }


    public PythonScriptBuilder replaceAllCode(String regex, int start, int end, Function<String, String> groupFunction) {
        return this.replaceAllCode(regex, matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            return groupFunction.apply(substring);
        });
    }

    public PythonScriptBuilder replaceAllCode(String regex, int start, int end, BiConsumer<String, StringBuilder> resultBuilderFunction) {
        return this.replaceAllCode(regex, matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            StringBuilder result = new StringBuilder();
            resultBuilderFunction.accept(substring, result);
            return result.toString();
        });
    }

    public PythonScriptBuilder replaceAllCode(String regex, Function<MatchResult, String> function) {
        this.script.clearBody();
        Pattern pattern = Pattern.compile(regex);
        List<String> codeLines = this.script.getCodeLines();
        for (int i = 0; i < codeLines.size(); i++) {
            String codeLine = codeLines.get(i);
            Matcher matcher = pattern.matcher(codeLine);
            codeLine = matcher.replaceAll(function);
            this.setCode(codeLine, i);
        }
        return this;
    }

    public PythonScript build() {
        return this.script;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PythonScriptBuilder) obj;
        return Objects.equals(this.script, that.script);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.script);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PythonScriptBuilder.class.getSimpleName() + "[", "]")
                .add("script=" + script)
                .toString();
    }
}
