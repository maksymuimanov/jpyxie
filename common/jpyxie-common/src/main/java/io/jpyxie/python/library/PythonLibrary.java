package io.jpyxie.python.library;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PythonLibrary {
    private String name;
    @Nullable
    private List<String> options;

    public PythonLibrary() {
        this("");
    }

    public PythonLibrary(String name) {
        this(name, new ArrayList<>());
    }

    public PythonLibrary(String name, List<String> options) {
        this.name = name;
        this.options = options;
    }

    public void addOption(String option) {
        Objects.requireNonNull(this.options);
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

    public void setOptions(@Nullable List<String> options) {
        this.options = options;
    }
}
