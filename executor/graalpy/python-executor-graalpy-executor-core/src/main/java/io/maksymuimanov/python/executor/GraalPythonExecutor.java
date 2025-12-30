package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.deserializer.PythonDeserializer;
import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
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
    protected PythonResultContainer execute(PythonScript script, PythonResultSpec resultSpec, Context interpreter) throws Exception {
        String name = String.valueOf(script.getSource());
        Source source = Source.newBuilder(PYTHON, script.toPythonString(), name)
                .cached(this.cached)
                .build();
        Value value = interpreter.eval(source);
        return resultSpec.collect((fieldName, type) -> {
            Value member = value.getMember(fieldName);
            return this.pythonDeserializer.deserialize(member, type);
        });
    }
}