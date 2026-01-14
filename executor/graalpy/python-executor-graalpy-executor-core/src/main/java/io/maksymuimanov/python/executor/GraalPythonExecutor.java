package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class GraalPythonExecutor extends InterpretablePythonExecutor<Value, Context> {
    public static final String PYTHON = "python";
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
        Source source = Source.newBuilder(PYTHON, script.toPythonString(), scriptSource)
                .cached(this.cached)
                .build();
        Value value = interpreter.eval(source);
        return this.createResultMap(resultSpec, value);
    }
}