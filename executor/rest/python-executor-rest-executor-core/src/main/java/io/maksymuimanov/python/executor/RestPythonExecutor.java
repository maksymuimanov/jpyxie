package io.maksymuimanov.python.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.http.PythonRestServerHttpRequestSender;
import io.maksymuimanov.python.script.PythonScript;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link PythonExecutor} interface that executes Python scripts via a REST endpoint.
 * <p>
 * This class sends the Python script to a remote REST service using HTTP POST with JSON payload,
 * then processes the response and converts it to the specified Java type.
 * <p>
 * The execution flow includes:
 * <ul>
 *   <li>Serializing the Python script wrapped in a {@link PythonRestRequest} to JSON.</li>
 *   <li>Sending an HTTP POST request to the configured REST endpoint with authentication headers.</li>
 *   <li>Receiving the JSON response and deserializing it into the expected body type.</li>
 * </ul>
 * <p>
 * Usage example:
 * <pre>{@code
 * PythonExecutor executor = new RestPythonExecutor(connectionDetails, objectMapper, httpClient);
 * String script = "print('Hello from Python via REST')";
 * String body = executor.execute(script, String.class);
 * }</pre>
 *
 * @see PythonExecutor
 * @see PythonRestRequest
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
public class RestPythonExecutor extends AbstractPythonExecutor<PythonRestResponse> {
    private final String uri;
    private final String token;
    private final PythonRestServerHttpRequestSender requestSender;
    private final ObjectMapper objectMapper;

    public RestPythonExecutor(PythonResultFieldNameProvider resultFieldProvider,
                                 String uri,
                                 String token,
                                 PythonRestServerHttpRequestSender requestSender,
                                 ObjectMapper objectMapper) {
        super(resultFieldProvider);
        this.uri = uri;
        this.token = token;
        this.requestSender = requestSender;
        this.objectMapper = objectMapper;
    }

    @Override
    public @Nullable <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription) {
        try {
            String scriptBody = script.toPythonString();
            List<String> fieldNames = List.of(resultDescription.fieldName());
            PythonRestRequest pythonRestRequest = new PythonRestRequest(scriptBody, fieldNames);
            String requestJson = this.objectMapper.writeValueAsString(pythonRestRequest);
            String responseJson = this.requestSender.send(this.uri, this. token, requestJson);
            PythonRestResponse pythonRestResponse = this.objectMapper.readValue(responseJson, PythonRestResponse.class);
            return this.getResult(resultDescription, pythonRestResponse);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    @Override
    public Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions) {
        try {
            String scriptBody = script.toPythonString();
            List<String> fieldNames = new ArrayList<>();
            for (PythonResultDescription<?> resultDescription : resultDescriptions) {
                fieldNames.add(resultDescription.fieldName());
            }
            PythonRestRequest pythonRestRequest = new PythonRestRequest(scriptBody, fieldNames);
            String requestJson = this.objectMapper.writeValueAsString(pythonRestRequest);
            String responseJson = this.requestSender.send(this.uri, this. token, requestJson);
            PythonRestResponse pythonRestResponse = objectMapper.readValue(responseJson, PythonRestResponse.class);
            return this.getResultMap(resultDescriptions, pythonRestResponse);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    protected <R> R getResult(PythonResultDescription<R> resultDescription, PythonRestResponse resultContainer) {
        return resultDescription.getValue((type, fieldName) -> (R) resultContainer.fields().get(fieldName));
    }
}