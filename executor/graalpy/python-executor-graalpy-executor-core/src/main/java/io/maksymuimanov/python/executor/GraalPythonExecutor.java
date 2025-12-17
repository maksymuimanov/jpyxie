package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.interpreter.PythonInterpreterProvider;
import io.maksymuimanov.python.response.PythonExecutionResponse;
import io.maksymuimanov.python.script.PythonScript;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.jspecify.annotations.Nullable;

public class GraalPythonExecutor extends InterpretablePythonExecutor<Context> {
    public static final String PYTHON = "python";
    private final String resultAppearance;
    private final boolean cached;

    public GraalPythonExecutor(PythonInterpreterProvider<Context> interpreterProvider, String resultAppearance, boolean cached) {
        super(interpreterProvider);
        this.resultAppearance = resultAppearance;
        this.cached = cached;
    }

    @Override
    protected <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass, Context interpreter) throws Exception {
        Source source = Source.newBuilder(PYTHON, script.toPythonString(), script.getSource())
                .cached(this.cached)
                .build();
        Value value = interpreter.eval(source);
        R result = resultClass == null || !script.containsDeepCode(this.resultAppearance)
                ? null
                : value.getMember(this.resultAppearance).as(resultClass);
        return new PythonExecutionResponse<>(result);
    }
}