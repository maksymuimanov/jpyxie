package io.jpyxie.python.executor;

import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.constant.PythonConstants;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.script.PythonScript;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class GraalPythonExecutor extends InterpretablePythonExecutor<Value, Context> {
    public static final boolean DEFAULT_CACHED = true;
    private final boolean cached;

    public GraalPythonExecutor(PythonDeserializer<Value> pythonDeserializer,
                               PythonInterpreterProvider<Context> interpreterProvider,
                               boolean cached) {
        super(pythonDeserializer, interpreterProvider);
        this.cached = cached;
    }

    @Override
    protected PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec, Context interpreter) throws Exception {
        String scriptSource = script.getName();
        Source source = Source.newBuilder(PythonConstants.PYTHON, script.toPythonString(), scriptSource)
                .cached(this.cached)
                .build();
        Value value = interpreter.eval(source);
        return this.createResultMap(resultSpec, value);
    }
}