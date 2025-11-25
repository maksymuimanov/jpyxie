package io.maksymuimanov.python.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PythonImportLine implements PythonRepresentation {
    public static final String IMPORT_REGEX = "(^import [\\w.]+$)|(^import [\\w.]+ as [\\w.]+$)|(^from [\\w.]+ import [\\w., ]+$)";
    /**
     * Regular expression for matching Python import statements with optional {@code from ... import ...} syntax.
     * <p>Used to detect imported variable names.</p>
     */
    public static final String IMPORT_VARIABLE_REGEX = "^\\s*(?:from\\s+([\\w.]+)\\s+import\\s+(.+)|import\\s+(.+))\\s*$";
    private final String line;
    private List<String> names;

    public PythonImportLine(String line) {
        this.line = line;
    }

    public List<String> findNames() {
        if (this.names != null) return this.names;
        Pattern pattern = Pattern.compile(IMPORT_VARIABLE_REGEX);
        Matcher matcher = pattern.matcher(this.line);
        if (!matcher.matches()) return Collections.emptyList();
        String fromImports = matcher.group(2);
        String importImports = matcher.group(3);
        String importsPart = fromImports != null ? fromImports : importImports;
        if (importsPart == null) return Collections.emptyList();
        String[] parts = importsPart.split(",\\s*");
        List<String> result = new ArrayList<>();
        for (String part : parts) {
            String[] nameAlias = part.trim().split("\\s+as\\s+");
            if (nameAlias.length == 2) {
                result.add(nameAlias[1]);
            } else {
                result.add(nameAlias[0]);
            }
        }

        this.names = result;
        return this.names;
    }

    public String getLine() {
        return line;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PythonImportLine) obj;
        return Objects.equals(this.line, that.line) &&
                Objects.equals(this.names, that.names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, names);
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
