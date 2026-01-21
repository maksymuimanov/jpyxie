package io.maksymuimanov.python.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonLibraryManagementException;
import io.maksymuimanov.python.http.PythonServerRequestSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RestPipManager implements PipManager {
    public static final String UNINSTALL_WITHOUT_CONFIRMATION_OPTION = "--yes";
    private final String uri;
    private final String token;
    private final PythonServerRequestSender requestSender;
    private final ObjectMapper objectMapper;

    @Override
    public boolean exists(PythonLibraryManagement management) {
        return this.executePipCommand(SHOW, management);
    }

    @Override
    public void install(PythonLibraryManagement management) {
        boolean isSuccessful = this.executePipCommand(INSTALL, management);
        if (!isSuccessful) {
            throw new PythonLibraryManagementException("Installation has failed");
        }
    }

    @Override
    public void uninstall(PythonLibraryManagement management) {
        management.addOption(UNINSTALL_WITHOUT_CONFIRMATION_OPTION);
        boolean isSuccessful = this.executePipCommand(UNINSTALL, management);
        if (!isSuccessful) {
            throw new PythonLibraryManagementException("Uninstallation has failed");
        }
    }

    protected boolean executePipCommand(String command, PythonLibraryManagement management) {
        try {
            PythonRestPipRequest pythonRestPipRequest = new PythonRestPipRequest(command, management.getName(), management.getOptions());
            String requestJson = this.objectMapper.writeValueAsString(pythonRestPipRequest);
            String responseJson = this.requestSender.send(this.uri, this.token, requestJson);
            return Boolean.parseBoolean(responseJson);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PythonLibraryManagementException(e);
        }
    }
}
