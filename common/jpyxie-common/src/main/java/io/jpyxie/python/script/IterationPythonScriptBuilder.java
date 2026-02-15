package io.jpyxie.python.script;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

public final class IterationPythonScriptBuilder extends AbstractPythonScriptBuilder {
    public static IterationPythonScriptBuilder of(PythonScript script) {
        return new IterationPythonScriptBuilder(script);
    }

    private IterationPythonScriptBuilder(PythonScript script) {
        super(script);
    }

    public IterationPythonScriptBuilder iterateImports(Consumer<PythonImportLine> action) {
        return this.iterateImports(action, true);
    }

    public IterationPythonScriptBuilder iterateImports(Consumer<PythonImportLine> action, boolean condition) {
        List<PythonImportLine> importLinesCopy = new ArrayList<>(this.getScript().getImportLines());
        return this.iterate(importLinesCopy, action, condition);
    }

    public IterationPythonScriptBuilder iterateImports(ObjIntConsumer<PythonImportLine> action) {
        return this.iterateImports(action, true);
    }

    
    public IterationPythonScriptBuilder iterateImports(ObjIntConsumer<PythonImportLine> action, boolean condition) {
        List<PythonImportLine> importLinesCopy = new ArrayList<>(this.getScript().getImportLines());
        return this.iterate(importLinesCopy, action, condition);
    }

    public IterationPythonScriptBuilder iterateCode(Consumer<PythonCodeLine> action) {
        return this.iterateCode(action, true);
    }

    public IterationPythonScriptBuilder iterateCode(Consumer<PythonCodeLine> action, boolean condition) {
        List<PythonCodeLine> codeLinesCopy = new ArrayList<>(this.getScript().getCodeLines());
        return this.iterate(codeLinesCopy, action, condition);
    }

    public IterationPythonScriptBuilder iterateCode(ObjIntConsumer<PythonCodeLine> action) {
        return this.iterateCode(action, true);
    }

    public IterationPythonScriptBuilder iterateCode(ObjIntConsumer<PythonCodeLine> action, boolean condition) {
        List<PythonCodeLine> codeLinesCopy = new ArrayList<>(this.getScript().getCodeLines());
        return this.iterate(codeLinesCopy, action, condition);
    }

    public <T> IterationPythonScriptBuilder iterate(Iterable<T> iterable, Consumer<T> action) {
        return this.iterate(iterable, action, true);
    }

    public <T> IterationPythonScriptBuilder iterate(Iterable<T> iterable, Consumer<T> action, boolean condition) {
        this.getScript().clearBody();
        if (condition) iterable.forEach(action);
        return this;
    }

    public <T> IterationPythonScriptBuilder iterate(List<T> iterable, ObjIntConsumer<T> action) {
        return this.iterate(iterable, action, true);
    }

    public <T> IterationPythonScriptBuilder iterate(List<T> iterable, ObjIntConsumer<T> action, boolean condition) {
        this.getScript().clearBody();
        if (condition) {
            for (int i = 0; i < iterable.size(); i++) {
                T element = iterable.get(i);
                action.accept(element, i);
            }
        }

        return this;
    }

    public IterationPythonScriptBuilder onCondition(Runnable action, boolean condition) {
        this.getScript().clearBody();
        if (condition) action.run();
        return this;
    }
}
