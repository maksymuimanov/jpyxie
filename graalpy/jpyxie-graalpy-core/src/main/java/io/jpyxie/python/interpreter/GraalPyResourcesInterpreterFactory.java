package io.jpyxie.python.interpreter;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.io.IOAccess;
import org.graalvm.python.embedding.GraalPyResources;
import org.graalvm.python.embedding.VirtualFileSystem;

import java.util.Map;

public class GraalPyResourcesInterpreterFactory extends AbstractGraalInterpreterFactory {
    private final VirtualFileSystem virtualFileSystem;

    public GraalPyResourcesInterpreterFactory(VirtualFileSystem virtualFileSystem) {
        this.virtualFileSystem = virtualFileSystem;
    }

    public GraalPyResourcesInterpreterFactory(VirtualFileSystem virtualFileSystem,
                                              IOAccess ioAccess,
                                              HostAccess hostAccess,
                                              boolean allowValueSharing,
                                              boolean allowCreateProcess,
                                              boolean allowExperimentalOptions,
                                              Map<String, String> additionalOptions) {
        super(ioAccess, hostAccess, allowValueSharing, allowCreateProcess, allowExperimentalOptions, additionalOptions);
        this.virtualFileSystem = virtualFileSystem;
    }

    @Override
    protected Context.Builder getContextBuilder() {
        return GraalPyResources.contextBuilder(this.virtualFileSystem);
    }
}