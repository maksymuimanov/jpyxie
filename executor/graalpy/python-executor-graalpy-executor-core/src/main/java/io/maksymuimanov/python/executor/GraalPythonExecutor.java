package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.script.PythonScript;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class GraalPythonExecutor extends InterpretablePythonExecutor<Value, Context> {
    public static final String PYTHON = "python";
    private final boolean cached;

    public GraalPythonExecutor(PythonInterpreterProvider<Context> interpreterProvider, boolean cached) {
        super(interpreterProvider);
        this.cached = cached;
    }

    @Override
    @Nullable
    protected <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription, Context interpreter) throws Exception {
        Source source = Source.newBuilder(PYTHON, script.toPythonString(), script.getSource())
                .cached(this.cached)
                .build();
        Value value = interpreter.eval(source);
        return this.getResult(resultDescription, value);
    }

    @Override
    protected Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Context interpreter) throws Exception {
        Source source = Source.newBuilder(PYTHON, script.toPythonString(), script.getSource())
                .cached(this.cached)
                .build();
        Value value = interpreter.eval(source);
        return this.getResultMap(resultDescriptions, value);
    }

    protected <R> @Nullable R getResult(PythonResultDescription<R> resultDescription, Value resultContainer) {
        return resultDescription.getValue((type, fieldName) -> resultContainer.getMember(fieldName).as(type));
    }
}