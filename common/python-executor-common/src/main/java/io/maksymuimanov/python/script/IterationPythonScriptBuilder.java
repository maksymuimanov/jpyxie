package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class IterationPythonScriptBuilder extends AbstractPythonScriptBuilder {
    public static IterationPythonScriptBuilder of(@NonNull PythonScript script) {
        return new IterationPythonScriptBuilder(script);
    }

    private IterationPythonScriptBuilder(@NonNull PythonScript script) {
        super(script);
    }

    public IterationPythonScriptBuilder iterateImportLines(@NonNull Consumer<PythonImportLine> action) {
        return this.iterateImportLines(action, true);
    }

    public IterationPythonScriptBuilder iterateImportLines(@NonNull Consumer<PythonImportLine> action, boolean condition) {
        List<PythonImportLine> importLinesCopy = new ArrayList<>(this.getScript().getImportLines());
        return this.iterate(importLinesCopy, action, condition);
    }

    public IterationPythonScriptBuilder iterateImportLines(@NonNull BiConsumer<PythonImportLine, Integer> action) {
        return this.iterateImportLines(action, true);
    }

    public IterationPythonScriptBuilder iterateImportLines(@NonNull BiConsumer<PythonImportLine, Integer> action, boolean condition) {
        List<PythonImportLine> importLinesCopy = new ArrayList<>(this.getScript().getImportLines());
        return this.iterate(importLinesCopy, action, condition);
    }

    public IterationPythonScriptBuilder iterateCodeLines(@NonNull Consumer<PythonCodeLine> action) {
        return this.iterateCodeLines(action, true);
    }

    public IterationPythonScriptBuilder iterateCodeLines(@NonNull Consumer<PythonCodeLine> action, boolean condition) {
        List<PythonCodeLine> codeLinesCopy = new ArrayList<>(this.getScript().getCodeLines());
        return this.iterate(codeLinesCopy, action, condition);
    }

    public IterationPythonScriptBuilder iterateCodeLines(@NonNull BiConsumer<PythonCodeLine, Integer> action) {
        return this.iterateCodeLines(action, true);
    }

    public IterationPythonScriptBuilder iterateCodeLines(@NonNull BiConsumer<PythonCodeLine, Integer> action, boolean condition) {
        List<PythonCodeLine> codeLinesCopy = new ArrayList<>(this.getScript().getCodeLines());
        return this.iterate(codeLinesCopy, action, condition);
    }

    public <T> IterationPythonScriptBuilder iterate(@NonNull Iterable<T> iterable, Consumer<T> action) {
        return this.iterate(iterable, action, true);
    }

    public <T> IterationPythonScriptBuilder iterate(@NonNull Iterable<T> iterable, Consumer<T> action, boolean condition) {
        this.getScript().clearBody();
        if (condition) iterable.forEach(action);
        return this;
    }

    public <T> IterationPythonScriptBuilder iterate(@NonNull List<T> iterable, BiConsumer<T, Integer> action) {
        return this.iterate(iterable, action, true);
    }

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

    public IterationPythonScriptBuilder doOnCondition(@NonNull Runnable action, boolean condition) {
        this.getScript().clearBody();
        if (condition) action.run();
        return this;
    }
}
