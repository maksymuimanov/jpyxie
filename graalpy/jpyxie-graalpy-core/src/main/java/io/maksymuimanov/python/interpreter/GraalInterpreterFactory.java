package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.executor.GraalPythonExecutor;
import lombok.RequiredArgsConstructor;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.jspecify.annotations.Nullable;

import java.util.Map;

@RequiredArgsConstructor
public class GraalInterpreterFactory implements PythonInterpreterFactory<Context> {
    private final HostAccess hostAccess;
    private final boolean allowValueSharing;
    private final boolean allowExperimentalOptions;
    private final Map<String, String> additionalOptions;

    @Override
    public Context create() {
        Context.Builder builder = Context.newBuilder(GraalPythonExecutor.PYTHON);
        builder.allowHostAccess(this.hostAccess);
        builder.allowValueSharing(this.allowValueSharing);
        builder.allowExperimentalOptions(this.allowExperimentalOptions);
        this.putOptions(builder, this.additionalOptions);
        return builder.build();
    }

    protected void putOptions(Context.Builder builder, @Nullable Map<String, String> options) {
        if (options != null && !options.isEmpty()) {
            options.forEach((key, value) -> this.putOption(builder, key, value));
        }
    }

    protected void putOption(Context.Builder builder, String key, @Nullable String value) {
        if (value != null && !value.isBlank()) {
            builder.option(key, value);
        }
    }
}
