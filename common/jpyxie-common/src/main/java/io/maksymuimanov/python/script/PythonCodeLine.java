package io.maksymuimanov.python.script;

import java.util.Objects;

public class PythonCodeLine implements PythonRepresentation {
    private String line;

    public PythonCodeLine(CharSequence line) {
        this.line = line.toString();
    }

    public boolean has(CharSequence sequence) {
        return this.line.contains(sequence);
    }

    public String getLine() {
        return line;
    }

    public void setLine(CharSequence line) {
        this.line = line.toString();
    }

    @Override
    public String toPythonString() {
        return this.getLine();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PythonCodeLine that = (PythonCodeLine) o;
        return Objects.equals(this.getLine(), that.getLine());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getLine());
    }
}
