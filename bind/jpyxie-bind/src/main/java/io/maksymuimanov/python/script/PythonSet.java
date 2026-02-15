package io.maksymuimanov.python.script;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PythonSet extends PythonValueContainer<Set<PythonRepresentation>> {
    public PythonSet() {
        this(new HashSet<>());
    }

    public PythonSet(Set<PythonRepresentation> value) {
        super(value);
    }

    public void add(PythonRepresentation value) {
        this.getValue().add(value);
    }

    @Override
    public String toPythonString() {
        return String.join("",
                "{",
                this.getValue()
                        .stream()
                        .map(PythonRepresentation::toPythonString)
                        .collect(Collectors.joining(", ")),
                "}"
        );
    }
}
