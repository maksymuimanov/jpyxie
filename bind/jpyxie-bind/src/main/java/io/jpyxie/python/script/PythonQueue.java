package io.jpyxie.python.script;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Collectors;

public class PythonQueue extends PythonValueContainer<Queue<PythonRepresentation>> {
    public PythonQueue() {
        this(new ArrayDeque<>());
    }

    public PythonQueue(Queue<PythonRepresentation> value) {
        super(value);
    }

    public void add(PythonRepresentation value) {
        this.getValue().add(value);
    }

    @Override
    public String toPythonString() {
        return String.join("",
                "deque([",
                this.getValue()
                        .stream()
                        .map(PythonRepresentation::toPythonString)
                        .collect(Collectors.joining(", ")),
                "])"
        );
    }
}
