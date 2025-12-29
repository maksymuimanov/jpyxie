package io.maksymuimanov.python.script;

import org.jspecify.annotations.NonNull;

public interface PythonScriptBuilder {
    @NonNull
    PythonScript getScript();
}
