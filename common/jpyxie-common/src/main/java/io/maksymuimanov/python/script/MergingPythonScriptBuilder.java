package io.maksymuimanov.python.script;

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
        script.getImportLines().forEach(basicPythonScriptBuilder::appendImport);
        basicPythonScriptBuilder.prependCode();
        for (int i = script.getCodeLines().size() - 1; i >= 0; i--) {
            PythonCodeLine codeLine = script.getCode(i);
            basicPythonScriptBuilder.prependCode(codeLine);
        }
        return this;
    }

    public MergingPythonScriptBuilder merge(PythonScript script) {
        script.getImportLines().forEach(basicPythonScriptBuilder::appendImport);
        basicPythonScriptBuilder.appendCode();
        script.getCodeLines().forEach(basicPythonScriptBuilder::appendCode);
        return this;
    }
}
