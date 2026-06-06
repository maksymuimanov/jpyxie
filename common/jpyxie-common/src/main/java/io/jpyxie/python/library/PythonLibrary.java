package io.jpyxie.python.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
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

    public void addOption(String option) {
        Objects.requireNonNull(this.options);
        this.options.add(option);
    }
}
