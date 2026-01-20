package io.maksymuimanov.python.library;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PythonLibraryManagement {
    private String name;
    private List<String> options;

    public PythonLibraryManagement() {
        this("");
    }

    public PythonLibraryManagement(String name) {
        this(name, new ArrayList<>());
    }

    public PythonLibraryManagement(String name, List<String> options) {
        this.name = name;
        this.options = options;
    }

    public void addOption(String option) {
        this.options.add(option);
    }

    public String getName() {
        return name;
    }

    @Nullable
    public List<String> getOptions() {
        return options;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
