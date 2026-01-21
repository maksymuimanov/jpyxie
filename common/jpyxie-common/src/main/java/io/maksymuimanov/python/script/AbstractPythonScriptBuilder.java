package io.maksymuimanov.python.script;

import java.util.function.Function;

public abstract class AbstractPythonScriptBuilder implements PythonScriptBuilder {
    private final PythonScript script;

    protected AbstractPythonScriptBuilder(PythonScript script) {
        this.script = script;
    }

    @Override
    public <B extends PythonScriptBuilder> B next(Function<PythonScript, B> switchFunction) {
        PythonScript pythonScript = this.getScript();
        return switchFunction.apply(pythonScript);
    }

    @Override
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
