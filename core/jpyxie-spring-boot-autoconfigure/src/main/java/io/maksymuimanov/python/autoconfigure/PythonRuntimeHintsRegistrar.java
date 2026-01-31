package io.maksymuimanov.python.autoconfigure;

import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class PythonRuntimeHintsRegistrar implements RuntimeHintsRegistrar {
    public static final String PYTHON_PATH_PATTERN = "python/.*\\.py";

    @Override
    public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
        hints.resources()
                .registerPattern(PYTHON_PATH_PATTERN);
    }
}
