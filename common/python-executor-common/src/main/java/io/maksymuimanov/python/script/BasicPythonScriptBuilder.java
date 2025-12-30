package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BasicPythonScriptBuilder extends AbstractPythonScriptBuilder {
    @NonNull
    public static BasicPythonScriptBuilder of(@NonNull PythonScript script) {
        return new BasicPythonScriptBuilder(script);
    }

    private BasicPythonScriptBuilder(@NonNull PythonScript script) {
        super(script);
    }

    @NonNull
    public BasicPythonScriptBuilder appendAll(@NonNull CharSequence script) {
        this.getScript().clearBody();
        script.toString()
                .lines()
                .forEach(this::append);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder append(@NonNull CharSequence line) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.appendImport(line)
                : this.appendCode(line);
    }

    @NonNull
    public BasicPythonScriptBuilder prepend(@NonNull CharSequence line) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.prependImport(line)
                : this.prependCode(line);
    }

    @NonNull
    public BasicPythonScriptBuilder insert(@NonNull CharSequence line, int index) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.insertImport(line, index)
                : this.insertCode(line, index);
    }

    @NonNull
    public BasicPythonScriptBuilder set(@NonNull CharSequence line, int index) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.setImport(line, index)
                : this.setCode(line, index);
    }

    @NonNull
    public BasicPythonScriptBuilder remove(@NonNull CharSequence line) {
        return line.toString().matches(PythonImportLine.IMPORT_REGEX)
                ? this.removeImport(line)
                : this.removeCode(line);
    }

    @NonNull
    public BasicPythonScriptBuilder appendImport(@NonNull CharSequence line) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.appendImport(importLine);
    }

    @NonNull
    public BasicPythonScriptBuilder appendImport(@NonNull PythonImportLine line) {
        this.getScript().clearBody();
        if (this.getScript().containsImport(line)) return this;
        this.getScript().getImportLines().add(line);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder prependImport(@NonNull CharSequence line) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.prependImport(importLine);
    }

    @NonNull
    public BasicPythonScriptBuilder prependImport(@NonNull PythonImportLine line) {
        this.getScript().clearBody();
        if (this.getScript().containsImport(line)) return this;
        this.getScript().getImportLines().add(PythonScript.START_INDEX, line);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder insertImport(@NonNull CharSequence line, int index) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.insertImport(importLine, index);
    }

    @NonNull
    public BasicPythonScriptBuilder insertImport(@NonNull PythonImportLine line, int index) {
        this.getScript().clearBody();
        this.getScript().getImportLines().add(index, line);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder setImport(CharSequence line, int index) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.setImport(importLine, index);
    }

    @NonNull
    public BasicPythonScriptBuilder setImport(PythonImportLine line, int index) {
        this.getScript().clearBody();
        this.getScript().getImportLines().set(index, line);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder removeImport(int index) {
        this.getScript().clearBody();
        this.getScript().getImportLines().remove(index);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder removeImport(CharSequence line) {
        PythonImportLine importLine = new PythonImportLine(line);
        return this.removeImport(importLine);
    }

    @NonNull
    public BasicPythonScriptBuilder removeImport(PythonImportLine line) {
        this.getScript().clearBody();
        this.getScript().getImportLines().remove(line);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder appendCode(@NonNull CharSequence... line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence codeLineElement : line) {
            stringBuilder.append(codeLineElement);
        }
        return this.appendCode(stringBuilder);
    }

    @NonNull
    public BasicPythonScriptBuilder appendCode(@NonNull CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.appendCode(codeLine);
    }

    @NonNull
    public BasicPythonScriptBuilder appendCode(@NonNull PythonCodeLine line) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().add(line);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder prependCode(@NonNull CharSequence... line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence codeLineElement : line) {
            stringBuilder.append(codeLineElement);
        }
        return this.prependCode(stringBuilder);
    }

    @NonNull
    public BasicPythonScriptBuilder prependCode(@NonNull CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.prependCode(codeLine);
    }

    @NonNull
    public BasicPythonScriptBuilder prependCode(@NonNull PythonCodeLine line) {
        this.getScript().clearBody();
        return this.insertCode(line, PythonScript.START_INDEX);
    }

    @NonNull
    public BasicPythonScriptBuilder insertCode(@NonNull CharSequence line, int index) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.insertCode(codeLine, index);
    }

    @NonNull
    public BasicPythonScriptBuilder insertCode(@NonNull PythonCodeLine line, int index) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().add(index, line);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder setCode(@NonNull CharSequence line, int index) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.setCode(codeLine, index);
    }

    @NonNull
    public BasicPythonScriptBuilder setCode(@NonNull PythonCodeLine line, int index) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().set(index, line);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder removeCode(int index) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().remove(index);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder removeCode(@NonNull CharSequence line) {
        PythonCodeLine codeLine = new PythonCodeLine(line);
        return this.removeCode(codeLine);
    }

    @NonNull
    public BasicPythonScriptBuilder removeCode(@NonNull PythonCodeLine line) {
        this.getScript().clearBody();
        this.getScript().getCodeLines().remove(line);
        return this;
    }

    @NonNull
    public BasicPythonScriptBuilder replaceAllCode(@NonNull CharSequence regex, int start, int end, @NonNull Function<String, String> groupFunction) {
        return this.replaceAllCode(regex, matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            return groupFunction.apply(substring);
        });
    }

    @NonNull
    public BasicPythonScriptBuilder replaceAllCode(@NonNull CharSequence regex, int start, int end, @NonNull BiConsumer<String, StringBuilder> resultBuilderFunction) {
        return this.replaceAllCode(regex, matchResult -> {
            String group = matchResult.group();
            String substring = group.substring(start, group.length() - end);
            StringBuilder result = new StringBuilder();
            resultBuilderFunction.accept(substring, result);
            return result.toString();
        });
    }

    @NonNull
    public BasicPythonScriptBuilder replaceAllCode(@NonNull CharSequence regex, @NonNull Function<MatchResult, String> function) {
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