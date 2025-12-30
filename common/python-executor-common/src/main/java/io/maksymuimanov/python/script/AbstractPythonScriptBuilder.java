package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

import java.util.function.Function;

public abstract class AbstractPythonScriptBuilder implements PythonScriptBuilder {
    @NonNull
    private final PythonScript script;

    protected AbstractPythonScriptBuilder(@NonNull PythonScript script) {
        this.script = script;
    }

    @Override
    @NonNull
    public <B extends PythonScriptBuilder> B next(Function<PythonScript, B> switchFunction) {
        PythonScript pythonScript = this.getScript();
        return switchFunction.apply(pythonScript);
    }

    @Override
    @NonNull
    public PythonScript getScript() {
        return this.script;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("AbstractPythonScriptBuilder{");
        stringBuilder.append("script=").append(script);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
