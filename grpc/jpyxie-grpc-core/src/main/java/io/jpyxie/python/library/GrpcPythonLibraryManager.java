package io.jpyxie.python.library;

import io.jpyxie.python.proto.GrpcPythonPipRequest;
import io.jpyxie.python.proto.GrpcPythonPipResponse;
import io.jpyxie.python.proto.PythonGrpcServiceGrpc;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GrpcPythonLibraryManager implements PythonLibraryManager {
    public static final String INSTALLATION_FAILURE_EXCEPTION_MESSAGE = "Installation has failed";
    public static final String UNINSTALLATION_FAILURE_EXCEPTION_MESSAGE = "Uninstallation has failed";
    private final PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub;

    @Override
    public boolean exists(PythonLibrary library) {
        return this.executePipCommand(SHOW_COMMAND, library);
    }

    @Override
    public void install(PythonLibrary library) {
        boolean isSuccessful = this.executePipCommand(INSTALL_COMMAND, library);
        if (!isSuccessful) {
            throw new PythonLibraryException(INSTALLATION_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void uninstall(PythonLibrary library) {
        library.addOption(UNINSTALL_WITHOUT_CONFIRMATION_OPTION);
        boolean isSuccessful = this.executePipCommand(UNINSTALL_COMMAND, library);
        if (!isSuccessful) {
            throw new PythonLibraryException(UNINSTALLATION_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    protected boolean executePipCommand(String name, PythonLibrary management) {
        try {
            GrpcPythonPipRequest pipRequest = GrpcPythonPipRequest.newBuilder()
                    .setName(name)
                    .setLibraryName(management.getName())
                    .addAllOptions(management.getOptions())
                    .build();
            GrpcPythonPipResponse pipResponse = stub.sendPip(pipRequest);
            return pipResponse.getSuccessful();
        } catch (Exception e) {
            throw new PythonLibraryException(e);
        }
    }
}
