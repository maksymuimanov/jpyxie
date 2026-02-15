package io.jpyxie.python.script;

public class PythonImportLine extends PythonCodeLine {
    public static final String IMPORT_REGEX = "(^import [\\w.]+$)|(^import [\\w.]+ as [\\w.]+$)|(^from [\\w.]+ import [\\w., ]+$)";

    public PythonImportLine(CharSequence line) {
        super(line);
    }
}
