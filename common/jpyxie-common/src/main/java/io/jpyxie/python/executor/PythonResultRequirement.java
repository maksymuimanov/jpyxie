package io.jpyxie.python.executor;

public record PythonResultRequirement<T>(String name, Class<T> type) {
}
