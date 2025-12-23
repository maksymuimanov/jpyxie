package io.maksymuimanov.python.library;

import org.jspecify.annotations.Nullable;

import java.util.List;

public record PythonRestPipRequest(String name, String libraryName, @Nullable List<String> options) {
}
