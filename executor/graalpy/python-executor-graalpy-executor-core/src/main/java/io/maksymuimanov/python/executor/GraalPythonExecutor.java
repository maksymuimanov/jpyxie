package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class GraalPythonExecutor extends InterpretablePythonExecutor<Context> {
    public static final String PYTHON = "python";
    private final PythonDeserializer<Value> pythonDeserializer;
    private final boolean cached;

    public GraalPythonExecutor(PythonInterpreterProvider<Context> interpreterProvider, PythonDeserializer<Value> pythonDeserializer, boolean cached) {
        super(interpreterProvider);
        this.pythonDeserializer = pythonDeserializer;
        this.cached = cached;
    }

    @Override
    protected PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec, Context interpreter) throws Exception {
        String scriptSource = String.valueOf(script.getSource());
        Source source = Source.newBuilder(PYTHON, script.toPythonString(), scriptSource)
                .cached(this.cached)
                .build();
        Value value = interpreter.eval(source);
        return PythonResultMap.of(resultSpec.getRequirements(), pythonDeserializer, value::getMember);
    }
}