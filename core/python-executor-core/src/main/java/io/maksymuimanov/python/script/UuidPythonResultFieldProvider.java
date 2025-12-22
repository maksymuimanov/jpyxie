package io.maksymuimanov.python.script;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
public class UuidPythonResultFieldProvider implements PythonResultFieldProvider {
    private final String prefix;

    @Override
    @NonNull
    public String provide() {
        return String.join("_", this.prefix, java.util.UUID.randomUUID().toString());
    }
}
