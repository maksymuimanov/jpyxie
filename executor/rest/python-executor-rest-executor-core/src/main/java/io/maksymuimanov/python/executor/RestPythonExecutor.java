package io.maksymuimanov.python.executor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.http.PythonServerRequestSender;
import io.maksymuimanov.python.processor.PythonResultMap;
import io.maksymuimanov.python.script.PythonScript;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RestPythonExecutor extends AbstractPythonExecutor<RestPythonResponse> {
    private final String uri;
    private final String token;
    private final PythonServerRequestSender requestSender;
    private final ObjectMapper objectMapper;

    public RestPythonExecutor(PythonDeserializer<RestPythonResponse> pythonDeserializer, String uri, String token, PythonServerRequestSender requestSender, ObjectMapper objectMapper) {
        super(pythonDeserializer);
        this.uri = uri;
        this.token = token;
        this.requestSender = requestSender;
        this.objectMapper = objectMapper;
    }

    @Override
    public PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec) {
        try {
            String scriptBody = script.toPythonString();
            List<String> fieldNames = resultSpec.stream()
                    .map(PythonResultRequirement::name)
                    .toList();
            RestPythonRequest restPythonRequest = new RestPythonRequest(scriptBody, fieldNames);
            String requestJson = this.objectMapper.writeValueAsString(restPythonRequest);
            String responseJson = this.requestSender.send(this.uri, this. token, requestJson);
            JsonNode jsonTree = this.objectMapper.readTree(responseJson);
            RestPythonResponse restPythonResponse = new RestPythonResponse(jsonTree);
            return this.createResultMap(resultSpec, restPythonResponse);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }
}