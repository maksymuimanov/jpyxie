package io.maksymuimanov.python.bind;

import io.maksymuimanov.python.script.PythonRepresentation;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PythonSet extends PythonValueContainer<Set<PythonRepresentation>> {
    public PythonSet() {
        this(new LinkedHashSet<>());
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
