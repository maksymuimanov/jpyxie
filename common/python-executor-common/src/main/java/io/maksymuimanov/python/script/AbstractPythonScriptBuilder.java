package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.StringJoiner;

public abstract class AbstractPythonScriptBuilder implements PythonScriptBuilder {
    @NonNull
    private final PythonScript script;

    protected AbstractPythonScriptBuilder(@NonNull PythonScript script) {
        this.script = script;
    }

    @Override
    @NonNull
    public PythonScript getScript() {
        return this.script;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BasicPythonScriptBuilder) obj;
        return Objects.equals(this.getScript(), that.getScript());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getScript());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BasicPythonScriptBuilder.class.getSimpleName() + "[", "]")
                .add("script=" + this.getScript())
                .toString();
    }
}
