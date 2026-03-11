package io.jpyxie.python.interpreter;

import lombok.AccessLevel;
import lombok.Getter;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.io.IOAccess;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

@Getter(AccessLevel.PROTECTED)
public abstract class AbstractGraalInterpreterFactory implements PythonInterpreterFactory<Context> {
    public static final IOAccess DEFAULT_IO_ACCESS = IOAccess.NONE;
    public static final HostAccess DEFAULT_HOST_ACCESS = HostAccess.NONE;
    public static final boolean DEFAULT_ALLOW_VALUE_SHARING = false;
    public static final boolean DEFAULT_ALLOW_CREATE_PROCESS = false;
    public static final boolean DEFAULT_ALLOW_EXPERIMENTAL_OPTIONS = false;
    public static final Map<String, String> DEFAULT_ADDITIONAL_OPTIONS = Collections.emptyMap();
    private final IOAccess ioAccess;
    private final HostAccess hostAccess;
    private final boolean allowValueSharing;
    private final boolean allowCreateProcess;
    private final boolean allowExperimentalOptions;
    private final Map<String, String> additionalOptions;

    protected AbstractGraalInterpreterFactory() {
        this(DEFAULT_IO_ACCESS, DEFAULT_HOST_ACCESS, DEFAULT_ALLOW_VALUE_SHARING, DEFAULT_ALLOW_CREATE_PROCESS, DEFAULT_ALLOW_EXPERIMENTAL_OPTIONS, DEFAULT_ADDITIONAL_OPTIONS);
    }

    protected AbstractGraalInterpreterFactory(IOAccess ioAccess,
                                              HostAccess hostAccess,
                                              boolean allowValueSharing,
                                              boolean allowCreateProcess,
                                              boolean allowExperimentalOptions,
                                              Map<String, String> additionalOptions) {
        this.ioAccess = ioAccess;
        this.hostAccess = hostAccess;
        this.allowValueSharing = allowValueSharing;
        this.allowCreateProcess = allowCreateProcess;
        this.allowExperimentalOptions = allowExperimentalOptions;
        this.additionalOptions = Map.copyOf(additionalOptions);
    }

    @Override
    public Context create() {
        Context.Builder builder = this.getContextBuilder();
        this.configureContext(builder);
        return builder.build();
    }

    protected abstract Context.Builder getContextBuilder();

    protected void configureContext(Context.Builder builder) {
        builder.allowIO(this.ioAccess);
        builder.allowHostAccess(this.hostAccess);
        builder.allowValueSharing(this.allowValueSharing);
        builder.allowCreateProcess(this.allowCreateProcess);
        builder.allowExperimentalOptions(this.allowExperimentalOptions);
        this.putOptions(builder, this.additionalOptions);
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
