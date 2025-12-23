package io.maksymuimanov.python.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.script.PythonScript;
import io.maksymuimanov.python.util.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static io.maksymuimanov.python.constant.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class RestPythonExecutorTests {
    @InjectMocks
    private RestPythonExecutor restPythonExecutor;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private HttpClient client;
    @Mock
    private HttpResponse<String> response;

    @BeforeEach
    void init() {
        TestUtils.setField(restPythonExecutor, "token", "token");
        TestUtils.setField(restPythonExecutor, "uri", "http://localhost:8000/script");
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {SIMPLE_SCRIPT_0, SIMPLE_SCRIPT_1, SIMPLE_SCRIPT_2, SIMPLE_SCRIPT_3})
    void testExecute(String script) {
        PythonScript pythonScript = new PythonScript(script);
        PythonRestRequest pythonRestRequest = new PythonRestRequest(script);

        Mockito.when(objectMapper.writeValueAsString(pythonRestRequest)).thenReturn("{\"script\": \"%s\"}".formatted(script));
        Mockito.when(client.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(response);
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn(OK);
        Mockito.when(objectMapper.readValue(OK, STRING_CLASS)).thenReturn(OK);

        String executed = restPythonExecutor.execute(pythonScript, STRING_CLASS).body();
        Assertions.assertEquals(OK, executed);
    }
}
