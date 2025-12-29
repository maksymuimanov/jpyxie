package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

public final class MergingPythonScriptBuilder extends AbstractPythonScriptBuilder {
    @NonNull
    private final BasicPythonScriptBuilder basicPythonScriptBuilder;

    public static MergingPythonScriptBuilder of(@NonNull PythonScript script) {
        return new MergingPythonScriptBuilder(script, BasicPythonScriptBuilder.of(script));
    }

    private MergingPythonScriptBuilder(@NonNull PythonScript script, @NonNull BasicPythonScriptBuilder basicPythonScriptBuilder) {
        super(script);
        this.basicPythonScriptBuilder = basicPythonScriptBuilder;
    }

    public MergingPythonScriptBuilder mergeToStart(@NonNull PythonRepresentation pythonRepresentation) {
        PythonScript pythonScript = new PythonScript(pythonRepresentation.toPythonString());
        return this.mergeToStart(pythonScript);
    }

    public MergingPythonScriptBuilder mergeToStart(@NonNull PythonScript script) {
        script.getImportLines().forEach(basicPythonScriptBuilder::appendImport);
        basicPythonScriptBuilder.prependCode();
        for (int i = script.getCodeLines().size() - 1; i >= 0; i--) {
            PythonCodeLine codeLine = script.getCode(i);
            basicPythonScriptBuilder.prependCode(codeLine);
        }
        return this;
    }

    public MergingPythonScriptBuilder merge(@NonNull PythonRepresentation pythonRepresentation) {
        PythonScript pythonScript = new PythonScript(pythonRepresentation.toPythonString());
        return this.merge(pythonScript);
    }

    public MergingPythonScriptBuilder merge(@NonNull PythonScript script) {
        script.getImportLines().forEach(basicPythonScriptBuilder::appendImport);
        basicPythonScriptBuilder.appendCode();
        script.getCodeLines().forEach(basicPythonScriptBuilder::appendCode);
        return this;
    }
}
