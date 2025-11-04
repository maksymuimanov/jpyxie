package io.maksymuimanov.python.schema;

import io.maksymuimanov.python.bind.PythonClass;
import io.maksymuimanov.python.bind.PythonSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PythonSchema implements PythonSerializable {
    private List<PythonClass> classes;

    public PythonSchema() {
        this(new ArrayList<>());
    }

    public PythonSchema(List<PythonClass> classes) {
        this.classes = classes;
    }

    public void addClass(PythonClass pythonClass) {
        this.getClasses().add(pythonClass);
    }

    public List<PythonClass> getClasses() {
        return classes;
    }

    public void setClasses(List<PythonClass> classes) {
        this.classes = classes;
    }

    @Override
    public String toPythonString() {
        return this.getClasses()
                .stream()
                .map(PythonClass::toPythonString)
                .collect(Collectors.joining("\n\n"));
    }
}
