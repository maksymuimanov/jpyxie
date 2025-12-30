package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.executor.PythonResultSpec;
import io.maksymuimanov.python.resolver.PythonArgumentSpec;

public record PythonContext(PythonResultSpec resultSpec, PythonArgumentSpec argumentSpec) {
}