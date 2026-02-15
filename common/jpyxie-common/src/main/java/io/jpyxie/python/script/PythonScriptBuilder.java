package io.jpyxie.python.script;

import java.util.function.Function;

public interface PythonScriptBuilder {
    <B extends PythonScriptBuilder> B next(Function<PythonScript, B> switchFunction);

    PythonScript getScript();
}