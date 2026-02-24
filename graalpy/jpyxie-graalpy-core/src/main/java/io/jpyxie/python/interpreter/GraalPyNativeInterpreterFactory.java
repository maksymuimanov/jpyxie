package io.jpyxie.python.interpreter;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.python.embedding.GraalPyResources;
import org.graalvm.python.embedding.VirtualFileSystem;

import java.util.Map;

public class GraalPyNativeInterpreterFactory extends AbstractGraalInterpreterFactory {
    private final VirtualFileSystem virtualFileSystem;

    public GraalPyNativeInterpreterFactory(VirtualFileSystem virtualFileSystem) {
        this.virtualFileSystem = virtualFileSystem;
    }

    public GraalPyNativeInterpreterFactory(HostAccess hostAccess,
                                           boolean allowValueSharing,
                                           boolean allowExperimentalOptions,
                                           Map<String, String> additionalOptions,
                                           VirtualFileSystem virtualFileSystem) {
        super(hostAccess, allowValueSharing, allowExperimentalOptions, additionalOptions);
        this.virtualFileSystem = virtualFileSystem;
    }

    @Override
    protected Context.Builder getContextBuilder() {
        return GraalPyResources.contextBuilder(this.virtualFileSystem);
    }
}