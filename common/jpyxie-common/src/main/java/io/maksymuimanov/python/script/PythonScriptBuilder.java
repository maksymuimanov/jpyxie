package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

import java.util.function.Function;

public interface PythonScriptBuilder {
    @NonNull
    <B extends PythonScriptBuilder> B next(Function<PythonScript, B> switchFunction);

    @NonNull
    PythonScript getScript();
}