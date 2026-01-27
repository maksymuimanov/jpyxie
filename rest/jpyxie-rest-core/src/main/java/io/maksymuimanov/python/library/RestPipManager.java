package io.maksymuimanov.python.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonLibraryManagementException;
import io.maksymuimanov.python.http.BasicPythonServerRequestSender;
import io.maksymuimanov.python.http.PythonServerRequestSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestPipManager implements PipManager {
    public static final String DEFAULT_ENDPOINT = "/pip";
    public static final String DEFAULT_URI = BasicPythonServerRequestSender.DEFAULT_HOST + ":" + BasicPythonServerRequestSender.DEFAULT_PORT + DEFAULT_ENDPOINT;
    public static final String INSTALLATION_FAILURE_EXCEPTION_MESSAGE = "Installation has failed";
    public static final String UNINSTALLATION_FAILURE_EXCEPTION_MESSAGE = "Uninstallation has failed";
    private final String uri;
    private final String token;
    private final PythonServerRequestSender requestSender;
    private final ObjectMapper objectMapper;

    public RestPipManager(PythonServerRequestSender requestSender,
                          ObjectMapper objectMapper) {
        this(DEFAULT_URI, BasicPythonServerRequestSender.DEFAULT_TOKEN, requestSender, objectMapper);
    }

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

    protected boolean executePipCommand(String command, PythonLibraryManagement management) {
        try {
            RestPythonPipRequest restPythonPipRequest = new RestPythonPipRequest(command, management.getName(), management.getOptions());
            String requestJson = this.objectMapper.writeValueAsString(restPythonPipRequest);
            String responseJson = this.requestSender.send(this.uri, this.token, requestJson);
            return Boolean.parseBoolean(responseJson);
        } catch (Exception e) {
            throw new PythonLibraryManagementException(e);
        }
    }
}
