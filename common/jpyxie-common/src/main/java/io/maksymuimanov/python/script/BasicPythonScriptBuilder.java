package io.maksymuimanov.python.script;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BasicPythonScriptBuilder extends AbstractPythonScriptBuilder {
    public static BasicPythonScriptBuilder of(PythonScript script) {
        return new BasicPythonScriptBuilder(script);
    }

    private BasicPythonScriptBuilder(PythonScript script) {
        super(script);
    }

    public BasicPythonScriptBuilder appendAll(CharSequence script) {
        this.getScript().clearBody();
        script.toString()
                .lines()
                .forEach(this::append);
        return this;
    }

    public BasicPythonScriptBuilder append(CharSequence line) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.appendImport(line)
                : this.appendCode(line);
    }

    public BasicPythonScriptBuilder prepend(CharSequence line) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.prependImport(line)
                : this.prependCode(line);
    }

    public BasicPythonScriptBuilder insert(CharSequence line, int index) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.insertImport(line, index)
                : this.insertCode(line, index);
    }

    public BasicPythonScriptBuilder set(CharSequence line, int index) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.setImport(line, index)
                : this.setCode(line, index);
    }

    public BasicPythonScriptBuilder remove(CharSequence line) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.removeImport(line)
                : this.removeCode(line);
    }

    public BasicPythonScriptBuilder appendImport(CharSequence line) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.appendImport(importLine);
    }

    public BasicPythonScriptBuilder appendImport(PythonImportLine line) {
        this.getScript().clearBody();
        if (this.getScript().containsImport(line)) return this;
        this.getScript().getImportLines().add(line);
        return this;
    }

    public BasicPythonScriptBuilder prependImport(CharSequence line) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.prependImport(importLine);
    }

    public BasicPythonScriptBuilder prependImport(PythonImportLine line) {
        this.getScript().clearBody();
        if (this.getScript().containsImport(line)) return this;
        this.getScript().getImportLines().add(PythonScript.START_INDEX, line);
        return this;
    }

    public BasicPythonScriptBuilder insertImport(CharSequence line, int index) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.insertImport(importLine, index);
    }

    public BasicPythonScriptBuilder insertImport(PythonImportLine line, int index) {
        this.getScript().clearBody();
        this.getScript().getImportLines().add(index, line);
        return this;
    }

    public BasicPythonScriptBuilder setImport(CharSequence line, int index) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.setImport(importLine, index);
    }

    public BasicPythonScriptBuilder setImport(PythonImportLine line, int index) {
        this.getScript().clearBody();
        this.getScript().getImportLines().set(index, line);
        return this;
    }

    public BasicPythonScriptBuilder removeImport(int index) {
        this.getScript().clearBody();
        this.getScript().getImportLines().remove(index);
        return this;
    }

    public BasicPythonScriptBuilder removeImport(CharSequence line) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.removeImport(importLine);
    }

    public BasicPythonScriptBuilder removeImport(PythonImportLine line) {
        this.getScript().clearBody();
        this.getScript().getImportLines().remove(line);
        return this;
    }

    public BasicPythonScriptBuilder appendCode(CharSequence... line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence codeLineElement : line) {
            stringBuilder.append(codeLineElement);
        }
        return this.appendCode(stringBuilder);
    }

    
    public BasicPythonScriptBuilder appendCode(CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.appendCode(codeLine);
    }

    public BasicPythonScriptBuilder appendCode(PythonCodeLine line) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().add(line);
        return this;
    }

    public BasicPythonScriptBuilder prependCode(CharSequence... line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence codeLineElement : line) {
            stringBuilder.append(codeLineElement);
        }
        return this.prependCode(stringBuilder);
    }

    public BasicPythonScriptBuilder prependCode(CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.prependCode(codeLine);
    }

    public BasicPythonScriptBuilder prependCode(PythonCodeLine line) {
        this.getScript().clearBody();
        return this.insertCode(line, PythonScript.START_INDEX);
    }

    public BasicPythonScriptBuilder insertCode(CharSequence line, int index) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.insertCode(codeLine, index);
    }

    public BasicPythonScriptBuilder insertCode(PythonCodeLine line, int index) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().add(index, line);
        return this;
    }

    public BasicPythonScriptBuilder setCode(CharSequence line, int index) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.setCode(codeLine, index);
    }

    public BasicPythonScriptBuilder setCode(PythonCodeLine line, int index) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().set(index, line);
        return this;
    }

    public BasicPythonScriptBuilder removeCode(int index) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().remove(index);
        return this;
    }

    public BasicPythonScriptBuilder removeCode(CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.removeCode(codeLine);
    }

    public BasicPythonScriptBuilder removeCode(PythonCodeLine line) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().remove(line);
        return this;
    }

    public BasicPythonScriptBuilder replaceAllCode(CharSequence regex, int start, int end, Function<String, String> groupFunction) {
        return this.replaceAllCode(regex, matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            return groupFunction.apply(substring);
        });
    }

    public BasicPythonScriptBuilder replaceAllCode(CharSequence regex, int start, int end, BiConsumer<String, StringBuilder> resultBuilderFunction) {
        return this.replaceAllCode(regex, matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            StringBuilder result = new StringBuilder();
            resultBuilderFunction.accept(substring, result);
            return result.toString();
        });
    }

    public BasicPythonScriptBuilder replaceAllCode(CharSequence regex, Function<MatchResult, String> function) {
        this.getScript().clearBody();
        Pattern pattern = Pattern.compile(regex.toString());
        List<PythonCodeLine> codeLines = this.getScript().getCodeLines();
        for (PythonCodeLine codeLine : codeLines) {
            CharSequence line = codeLine.getLine();
            Matcher matcher = pattern.matcher(line);
            codeLine.setLine(matcher.replaceAll(function));
        }
        return this;
    }
}