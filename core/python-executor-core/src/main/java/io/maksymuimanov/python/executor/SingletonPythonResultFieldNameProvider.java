package io.maksymuimanov.python.executor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SingletonPythonResultFieldNameProvider implements PythonResultFieldNameProvider {
    private final String fieldName;

    @Override
    public String get() {
        return this.fieldName;
    }
}
