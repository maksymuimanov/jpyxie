package io.maksymuimanov.python.interpreter;

import io.maksymuimanov.python.executor.GraalPythonExecutor;
import lombok.RequiredArgsConstructor;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.jspecify.annotations.Nullable;

import java.util.Properties;

@RequiredArgsConstructor
public class GraalInterpreterFactory implements PythonInterpreterFactory<Context> {
    public static final String PYTHON_CORE_HOME = "python.CoreHome";
    public static final String PYTHON_HOME = "python.PythonHome";
    public static final String PYTHON_CHECK_HASH_PYCS_MODE = "python.CheckHashPycsMode";
    public static final String PYTHON_WARNING_EXPERIMENTAL_FEATURES = "python.WarningExperimentalFeatures";
    public static final String PYTHON_POSIX_MODULE_BACKEND = "python.PosixModuleBackend";
    public static final String PYTHON_EXECUTABLE = "python.Executable";
    public static final String PYTHON_SYS_BASE_PREFIX = "python.SysBasePrefix";
    private final String coreHome;
    private final String pythonHome;
    private final String checkHashPycsMode;
    private final boolean warningExperimentalFeatures;
    private final String posixModuleBackend;
    private final String executable;
    private final String sysBasePrefix;
    private final HostAccess hostAccess;
    private final boolean allowValueSharing;
    private final boolean allowExperimentalOptions;
    private final Properties additionalOptions;

    @Override
    public Context create() {
        Context.Builder builder = Context.newBuilder(GraalPythonExecutor.PYTHON);
        this.putOption(builder, PYTHON_CORE_HOME, this.coreHome);
        this.putOption(builder, PYTHON_HOME, this.pythonHome);
        this.putOption(builder, PYTHON_CHECK_HASH_PYCS_MODE, this.checkHashPycsMode);
        this.putOption(builder, PYTHON_WARNING_EXPERIMENTAL_FEATURES, String.valueOf(this.warningExperimentalFeatures));
        this.putOption(builder, PYTHON_POSIX_MODULE_BACKEND, this.posixModuleBackend);
        this.putOption(builder, PYTHON_EXECUTABLE, this.executable);
        this.putOption(builder, PYTHON_SYS_BASE_PREFIX, this.sysBasePrefix);
        builder.allowHostAccess(this.hostAccess);
        builder.allowValueSharing(this.allowValueSharing);
        builder.allowExperimentalOptions(this.allowExperimentalOptions);
        this.putProperties(builder, this.additionalOptions);
        return builder.build();
    }

    private void putProperties(Context.Builder builder, @Nullable Properties properties) {
        if (properties != null && !properties.isEmpty()) {
            properties.forEach((key, value) -> this.putOption(builder, key.toString(), value.toString()));
        }
    }

    private void putOption(Context.Builder builder, String key, @Nullable String value) {
        if (value != null && !value.isBlank()) {
            builder.option(key, value);
        }
    }
}
