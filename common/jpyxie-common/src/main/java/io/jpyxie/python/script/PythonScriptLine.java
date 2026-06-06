package io.jpyxie.python.script;

import io.jpyxie.python.PythonRepresentation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PythonScriptLine implements PythonRepresentation {
    private String line;

    public PythonScriptLine(CharSequence line) {
        this.line = line.toString();
    }

    public boolean contains(CharSequence sequence) {
        return this.line.contains(sequence);
    }

    @Override
    public String toString() {
        return this.toPythonString();
    }

    @Override
    public String toPythonString() {
        return this.getLine();
    }
}
