package io.jpyxie.python.script;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PythonList extends PythonValueContainer<List<PythonRepresentation>> {
    public PythonList() {
        this(new ArrayList<>());
    }

    public PythonList(List<PythonRepresentation> value) {
        super(value);
    }

    public void add(PythonRepresentation value) {
        this.getValue().add(value);
    }

    @Override
    public String toPythonString() {
        return String.join("",
                "[",
                this.getValue()
                        .stream()
                        .map(PythonRepresentation::toPythonString)
                        .collect(Collectors.joining(", ")),
                "]"
        );
    }
}
