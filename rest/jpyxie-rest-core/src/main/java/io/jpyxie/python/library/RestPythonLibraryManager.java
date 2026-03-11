package io.jpyxie.python.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jpyxie.python.exception.PythonLibraryManagementException;
import io.jpyxie.python.http.BasicPythonServerRequestSender;
import io.jpyxie.python.http.PythonServerRequestSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestPythonLibraryManager implements PythonLibraryManager {
    public static final String DEFAULT_ENDPOINT = "/pip";
    public static final String DEFAULT_URI = BasicPythonServerRequestSender.DEFAULT_HOST + ":" + BasicPythonServerRequestSender.DEFAULT_PORT + DEFAULT_ENDPOINT;
    public static final String INSTALLATION_FAILURE_EXCEPTION_MESSAGE = "Installation has failed";
    public static final String UNINSTALLATION_FAILURE_EXCEPTION_MESSAGE = "Uninstallation has failed";
    private final String uri;
    private final String token;
    private final PythonServerRequestSender requestSender;
    private final ObjectMapper objectMapper;

    public RestPythonLibraryManager(PythonServerRequestSender requestSender,
                                    ObjectMapper objectMapper) {
        this(DEFAULT_URI, BasicPythonServerRequestSender.DEFAULT_TOKEN, requestSender, objectMapper);
    }

    @Override
    public boolean exists(PythonLibrary management) {
        return this.executePipCommand(SHOW, management);
    }

    @Override
    public void install(PythonLibrary management) {
        boolean isSuccessful = this.executePipCommand(INSTALL, management);
        if (!isSuccessful) {
            throw new PythonLibraryManagementException(INSTALLATION_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void uninstall(PythonLibrary management) {
        management.addOption(UNINSTALL_WITHOUT_CONFIRMATION_OPTION);
        boolean isSuccessful = this.executePipCommand(UNINSTALL, management);
        if (!isSuccessful) {
            throw new PythonLibraryManagementException(UNINSTALLATION_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    protected boolean executePipCommand(String command, PythonLibrary management) {
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
