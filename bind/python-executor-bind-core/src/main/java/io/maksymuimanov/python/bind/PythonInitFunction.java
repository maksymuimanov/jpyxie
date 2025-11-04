package io.maksymuimanov.python.bind;

import java.util.List;
import java.util.stream.Collectors;

public class PythonInitFunction implements PythonSerializable {
    public static final String INIT_FUNCTION = "\tdef __init__(self, %s):\n";
    public static final String SELF_PROPERTY = "self.";
    private List<PythonMethodParameter> fields;

    public PythonInitFunction(List<PythonMethodParameter> fields) {
        this.fields = fields;
    }

    public List<PythonMethodParameter> getParameters() {
        return fields;
    }

    public void setFields(List<PythonMethodParameter> fields) {
        this.fields = fields;
    }

    @Override
    public String toPythonString() {
        return String.join("", INIT_FUNCTION.formatted(fields.stream()
                .map(field -> field.getType() == null ? field.toPythonString() : field.toPythonString() + ": " + field.getType())
                .collect(Collectors.joining(", "))), fields.stream()
                .map(PythonMethodParameter::toPythonString)
                .map(field -> String.join("", SELF_PROPERTY, field, " = ", field))
                .map(field -> "\t\t" + field)
                .collect(Collectors.joining("\n")));
    }
}