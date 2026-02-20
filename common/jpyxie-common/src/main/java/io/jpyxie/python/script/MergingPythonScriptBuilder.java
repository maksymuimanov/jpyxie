package io.jpyxie.python.script;

public final class MergingPythonScriptBuilder extends AbstractPythonScriptBuilder {
    private final BasicPythonScriptBuilder basicPythonScriptBuilder;

    public static MergingPythonScriptBuilder of(PythonScript script) {
        return new MergingPythonScriptBuilder(script, BasicPythonScriptBuilder.of(script));
    }

    private MergingPythonScriptBuilder(PythonScript script, BasicPythonScriptBuilder basicPythonScriptBuilder) {
        super(script);
        this.basicPythonScriptBuilder = basicPythonScriptBuilder;
    }

    public MergingPythonScriptBuilder mergeToStart(PythonScript script) {
        for (int i = script.getImportsSize() - 1; i >= 0; i--) {
            PythonImportLine importLine = script.getImport(i);
            basicPythonScriptBuilder.prependImport(importLine);
        }
        for (int i = script.getCodeSize() - 1; i >= 0; i--) {
            PythonCodeLine codeLine = script.getCode(i);
            basicPythonScriptBuilder.prependCode(codeLine);
        }
        return this;
    }

    public MergingPythonScriptBuilder merge(PythonScript script) {
        script.importLines()
                .forEach(basicPythonScriptBuilder::appendImport);
        script.codeLines()
                .forEach(basicPythonScriptBuilder::appendCode);
        return this;
    }
}
