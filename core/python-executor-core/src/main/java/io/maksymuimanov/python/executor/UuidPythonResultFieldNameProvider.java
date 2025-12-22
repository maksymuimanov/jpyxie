package io.maksymuimanov.python.executor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UuidPythonResultFieldNameProvider implements PythonResultFieldNameProvider {
    private final String prefix;

    @Override
    public String get() {
        return String.join("_", this.prefix, java.util.UUID.randomUUID().toString());
    }
}
