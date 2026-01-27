package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.constant.PythonConstants;
import lombok.RequiredArgsConstructor;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class GraalInterpreterFactory implements PythonInterpreterFactory<Context> {
    public static final HostAccess DEFAULT_HOST_ACCESS = HostAccess.NONE;
    public static final boolean DEFAULT_ALLOW_VALUE_SHARING = false;
    public static final boolean DEFAULT_ALLOW_EXPERIMENTAL_OPTIONS = false;
    public static final Map<String, String> DEFAULT_ADDITIONAL_OPTIONS = Collections.emptyMap();
    private final HostAccess hostAccess;
    private final boolean allowValueSharing;
    private final boolean allowExperimentalOptions;
    private final Map<String, String> additionalOptions;

    public GraalInterpreterFactory() {
        this(DEFAULT_HOST_ACCESS, DEFAULT_ALLOW_VALUE_SHARING, DEFAULT_ALLOW_EXPERIMENTAL_OPTIONS, DEFAULT_ADDITIONAL_OPTIONS);
    }

    @Override
    public Context create() {
        Context.Builder builder = Context.newBuilder(PythonConstants.PYTHON);
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