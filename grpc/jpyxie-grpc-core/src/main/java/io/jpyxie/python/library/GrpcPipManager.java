package io.jpyxie.python.library;

import io.jpyxie.python.exception.PythonLibraryManagementException;
import io.jpyxie.python.proto.GrpcPythonPipRequest;
import io.jpyxie.python.proto.GrpcPythonPipResponse;
import io.jpyxie.python.proto.PythonGrpcServiceGrpc;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GrpcPipManager implements PipManager {
    public static final String INSTALLATION_FAILURE_EXCEPTION_MESSAGE = "Installation has failed";
    public static final String UNINSTALLATION_FAILURE_EXCEPTION_MESSAGE = "Uninstallation has failed";
    private final PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub;

    @Override
    public boolean exists(PythonLibraryManagement management) {
        return this.executePipCommand(SHOW, management);
    }

    @Override
    public void install(PythonLibraryManagement management) {
        boolean isSuccessful = this.executePipCommand(INSTALL, management);
        if (!isSuccessful) {
            throw new PythonLibraryManagementException(INSTALLATION_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void uninstall(PythonLibraryManagement management) {
        management.addOption(UNINSTALL_WITHOUT_CONFIRMATION_OPTION);
        boolean isSuccessful = this.executePipCommand(UNINSTALL, management);
        if (!isSuccessful) {
            throw new PythonLibraryManagementException(UNINSTALLATION_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    protected boolean executePipCommand(String name, PythonLibraryManagement management) {
        try {
            GrpcPythonPipRequest pipRequest = GrpcPythonPipRequest.newBuilder()
                    .setName(name)
                    .setLibraryName(management.getName())
                    .addAllOptions(management.getOptions())
                    .build();
            GrpcPythonPipResponse pipResponse = stub.sendPip(pipRequest);
            return pipResponse.getSuccessful();
        } catch (Exception e) {
            throw new PythonLibraryManagementException(e);
        }
    }
}
