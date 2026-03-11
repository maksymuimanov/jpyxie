package io.jpyxie.python.interpreter;

import io.jpyxie.python.constant.PythonConstants;
import io.jpyxie.python.environment.PythonEnvironment;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.io.IOAccess;

import java.util.Map;

public class GraalInterpreterFactory extends AbstractGraalInterpreterFactory {
    public static final String PYTHON_EXECUTABLE = "python.Executable";
    private final PythonEnvironment pythonEnvironment;

    public GraalInterpreterFactory(PythonEnvironment pythonEnvironment) {
        super();
        this.pythonEnvironment = pythonEnvironment;
    }

    public GraalInterpreterFactory(PythonEnvironment pythonEnvironment,
                                   IOAccess ioAccess,
                                   HostAccess hostAccess,
                                   boolean allowValueSharing,
                                   boolean allowCreateProcess,
                                   boolean allowExperimentalOptions,
                                   Map<String, String> additionalOptions) {
        super(ioAccess, hostAccess, allowValueSharing, allowCreateProcess, allowExperimentalOptions, additionalOptions);
        this.pythonEnvironment = pythonEnvironment;
    }

    @Override
    protected Context.Builder getContextBuilder() {
        return Context.newBuilder(PythonConstants.PYTHON);
    }

    @Override
    protected void configureContext(Context.Builder builder) {
        String pythonExecutable = this.pythonEnvironment.getExecutableOrBackup();
        builder.option(PYTHON_EXECUTABLE, pythonExecutable);
        super.configureContext(builder);
    }
}