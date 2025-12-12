package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.executor.GraalPythonExecutor;
import lombok.RequiredArgsConstructor;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.jspecify.annotations.Nullable;

import java.util.Properties;

@RequiredArgsConstructor
public class GraalInterpreterFactory implements PythonInterpreterFactory<Context> {
    private final HostAccess hostAccess;
    private final boolean allowValueSharing;
    private final boolean allowExperimentalOptions;
    private final Properties additionalOptions;

    @Override
    public Context create() {
        Context.Builder builder = Context.newBuilder(GraalPythonExecutor.PYTHON);
        builder.allowHostAccess(this.hostAccess);
        builder.allowValueSharing(this.allowValueSharing);
        builder.allowExperimentalOptions(this.allowExperimentalOptions);
        this.putProperties(builder, this.additionalOptions);
        return builder.build();
    }

    protected void putProperties(Context.Builder builder, @Nullable Properties properties) {
        if (properties != null && !properties.isEmpty()) {
            properties.forEach((key, value) -> this.putOption(builder, key.toString(), value.toString()));
        }
    }

    protected void putOption(Context.Builder builder, String key, @Nullable String value) {
        if (value != null && !value.isBlank()) {
            builder.option(key, value);
        }
    }
}
