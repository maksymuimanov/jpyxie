package io.maksymuimanov.python.script;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PythonDictionary extends PythonValueContainer<Map<PythonRepresentation, PythonRepresentation>> {
    public PythonDictionary() {
        this(new HashMap<>());
    }

    public PythonDictionary(Map<PythonRepresentation, PythonRepresentation> value) {
        super(value);
    }

    public void put(PythonRepresentation key, PythonRepresentation value) {
        this.getValue().put(key, value);
    }

    @Override
    public String toPythonString() {
        return String.join("",
                "{\n",
                this.getValue()
                        .entrySet()
                        .stream()
                        .map(entry -> Map.entry(entry.getKey().toPythonString(), entry.getValue().toPythonString()))
                        .map(entry -> "\t" + entry.getKey() + ": " + entry.getValue()
                                .lines()
                                .collect(Collectors.joining("\n\t")))
                        .collect(Collectors.joining(",\n")),
                "\n}"
        );
    }
}
