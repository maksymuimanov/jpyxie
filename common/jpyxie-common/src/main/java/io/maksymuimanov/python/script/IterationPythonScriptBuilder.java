package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class IterationPythonScriptBuilder extends AbstractPythonScriptBuilder {
    @NonNull
    public static IterationPythonScriptBuilder of(@NonNull PythonScript script) {
        return new IterationPythonScriptBuilder(script);
    }

    private IterationPythonScriptBuilder(@NonNull PythonScript script) {
        super(script);
    }

    @NonNull
    public IterationPythonScriptBuilder iterateImports(@NonNull Consumer<PythonImportLine> action) {
        return this.iterateImports(action, true);
    }

    @NonNull
    public IterationPythonScriptBuilder iterateImports(@NonNull Consumer<PythonImportLine> action, boolean condition) {
        List<PythonImportLine> importLinesCopy = new ArrayList<>(this.getScript().getImportLines());
        return this.iterate(importLinesCopy, action, condition);
    }

    @NonNull
    public IterationPythonScriptBuilder iterateImports(@NonNull BiConsumer<PythonImportLine, Integer> action) {
        return this.iterateImports(action, true);
    }

    @NonNull
    public IterationPythonScriptBuilder iterateImports(@NonNull BiConsumer<PythonImportLine, Integer> action, boolean condition) {
        List<PythonImportLine> importLinesCopy = new ArrayList<>(this.getScript().getImportLines());
        return this.iterate(importLinesCopy, action, condition);
    }

    @NonNull
    public IterationPythonScriptBuilder iterateCode(@NonNull Consumer<PythonCodeLine> action) {
        return this.iterateCode(action, true);
    }

    @NonNull
    public IterationPythonScriptBuilder iterateCode(@NonNull Consumer<PythonCodeLine> action, boolean condition) {
        List<PythonCodeLine> codeLinesCopy = new ArrayList<>(this.getScript().getCodeLines());
        return this.iterate(codeLinesCopy, action, condition);
    }

    @NonNull
    public IterationPythonScriptBuilder iterateCode(@NonNull BiConsumer<PythonCodeLine, Integer> action) {
        return this.iterateCode(action, true);
    }

    @NonNull
    public IterationPythonScriptBuilder iterateCode(@NonNull BiConsumer<PythonCodeLine, Integer> action, boolean condition) {
        List<PythonCodeLine> codeLinesCopy = new ArrayList<>(this.getScript().getCodeLines());
        return this.iterate(codeLinesCopy, action, condition);
    }

    @NonNull
    public <T> IterationPythonScriptBuilder iterate(@NonNull Iterable<T> iterable, Consumer<T> action) {
        return this.iterate(iterable, action, true);
    }

    @NonNull
    public <T> IterationPythonScriptBuilder iterate(@NonNull Iterable<T> iterable, Consumer<T> action, boolean condition) {
        this.getScript().clearBody();
        if (condition) iterable.forEach(action);
        return this;
    }

    @NonNull
    public <T> IterationPythonScriptBuilder iterate(@NonNull List<T> iterable, BiConsumer<T, Integer> action) {
        return this.iterate(iterable, action, true);
    }

    @NonNull
    public <T> IterationPythonScriptBuilder iterate(@NonNull List<T> iterable, BiConsumer<T, Integer> action, boolean condition) {
        this.getScript().clearBody();
        if (condition) {
            for (int i = 0; i < iterable.size(); i++) {
                T element = iterable.get(i);
                action.accept(element, i);
            }
        }

        return this;
    }

    @NonNull
    public IterationPythonScriptBuilder onCondition(@NonNull Runnable action, boolean condition) {
        this.getScript().clearBody();
        if (condition) action.run();
        return this;
    }
}
