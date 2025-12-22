package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface PythonResultFieldProvider {
    @NonNull
    String provide();
}
