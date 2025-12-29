package io.maksymuimanov.python.script;

import io.maksymuimanov.python.util.CharSequenceUtils;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class PythonCodeLine implements PythonRepresentation {
    private CharSequence line;

    public PythonCodeLine(CharSequence line) {
        this.line = line;
    }

    public boolean has(CharSequence sequence) {
        return CharSequenceUtils.contains(this.line, sequence);
    }

    public CharSequence getLine() {
        return line;
    }

    public void setLine(@NonNull CharSequence line) {
        this.line = line;
    }

    @Override
    public String toPythonString() {
        return this.getLine().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PythonCodeLine that = (PythonCodeLine) o;
        return Objects.equals(this.getLine().toString(), that.getLine().toString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getLine());
    }
}
