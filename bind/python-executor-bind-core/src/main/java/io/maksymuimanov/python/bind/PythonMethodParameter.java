package io.maksymuimanov.python.bind;

import org.jspecify.annotations.Nullable;

public class PythonMethodParameter implements PythonSerializable, PythonTyped {
    private String name;
    @Nullable
    private String type;

    public PythonMethodParameter(String name) {
        this(name, null);
    }

    public PythonMethodParameter(String name, @Nullable String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @Nullable
    public String getType() {
        return type;
    }

    @Override
    public void setType(@Nullable String type) {
        this.type = type;
    }

    @Override
    public String toPythonString() {
        return this.getName();
    }
}