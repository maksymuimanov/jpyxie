package io.maksymuimanov.python.script;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
public class SingletonePythonResultFieldProvider implements PythonResultFieldProvider {
    private final String fieldName;

    @Override
    @NonNull
    public String provide() {
        return this.fieldName;
    }
}
