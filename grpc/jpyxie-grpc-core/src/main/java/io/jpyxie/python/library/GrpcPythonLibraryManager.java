package io.jpyxie.python.library;

import io.jpyxie.python.proto.GrpcPythonPipRequest;
import io.jpyxie.python.proto.GrpcPythonPipResponse;
import io.jpyxie.python.proto.PythonGrpcServiceGrpc;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GrpcPythonLibraryManager implements PythonLibraryManager {
    private final PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub;

    @Override
    public boolean exists(PythonLibrary library) {
        return this.executePipCommand(SHOW_COMMAND, library);
    }

    @Override
    public void install(PythonLibrary library) {
        boolean isSuccessful = this.executePipCommand(INSTALL_COMMAND, library);
        if (!isSuccessful) {
            throw GrpcPythonLibraryManagerException.failedToInstall(library);
        }
    }

    @Override
    public void uninstall(PythonLibrary library) {
        library.addOption(UNINSTALL_WITHOUT_CONFIRMATION_OPTION);
        boolean isSuccessful = this.executePipCommand(UNINSTALL_COMMAND, library);
        if (!isSuccessful) {
            throw GrpcPythonLibraryManagerException.failedToUninstall(library);
        }
    }

    protected boolean executePipCommand(String command, PythonLibrary management) {
        try {
            GrpcPythonPipRequest pipRequest = GrpcPythonPipRequest.newBuilder()
                    .setCommand(command)
                    .setLibraryName(management.getName())
                    .addAllOptions(management.getOptions())
                    .build();
            GrpcPythonPipResponse pipResponse = stub.sendPip(pipRequest);
            return pipResponse.getSuccessful();
        } catch (Exception e) {
            throw GrpcPythonLibraryManagerException.failedToExecutePipCommand(command, management, e);
        }
    }
}
