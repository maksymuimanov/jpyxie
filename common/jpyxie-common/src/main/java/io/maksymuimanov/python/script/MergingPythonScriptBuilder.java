package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

public final class MergingPythonScriptBuilder extends AbstractPythonScriptBuilder {
    @NonNull
    private final BasicPythonScriptBuilder basicPythonScriptBuilder;

    @NonNull
    public static MergingPythonScriptBuilder of(@NonNull PythonScript script) {
        return new MergingPythonScriptBuilder(script, BasicPythonScriptBuilder.of(script));
    }

    private MergingPythonScriptBuilder(@NonNull PythonScript script, @NonNull BasicPythonScriptBuilder basicPythonScriptBuilder) {
        super(script);
        this.basicPythonScriptBuilder = basicPythonScriptBuilder;
    }

    @NonNull
    public MergingPythonScriptBuilder mergeToStart(@NonNull PythonScript script) {
        script.getImportLines().forEach(basicPythonScriptBuilder::appendImport);
        basicPythonScriptBuilder.prependCode();
        for (int i = script.getCodeLines().size() - 1; i >= 0; i--) {
            PythonCodeLine codeLine = script.getCode(i);
            basicPythonScriptBuilder.prependCode(codeLine);
        }
        return this;
    }

    @NonNull
    public MergingPythonScriptBuilder merge(@NonNull PythonScript script) {
        script.getImportLines().forEach(basicPythonScriptBuilder::appendImport);
        basicPythonScriptBuilder.appendCode();
        script.getCodeLines().forEach(basicPythonScriptBuilder::appendCode);
        return this;
    }
}
