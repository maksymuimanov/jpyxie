package io.maksymuimanov.python.http;

import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.exception.PythonHttpRequestSendingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class BasicPythonServerRequestSender implements PythonServerRequestSender {
    private final HttpClient httpClient;

    @Override
    public String send(String uri, String token, String requestJson, String... headers) {
        try {
            HttpRequest.BodyPublisher requestPayload = HttpRequest.BodyPublishers.ofString(requestJson);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
                    .header(TOKEN_HEADER, token)
                    .headers(headers)
                    .POST(requestPayload)
                    .build();
            HttpResponse.BodyHandler<String> stringBodyHandler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = httpClient.send(request, stringBodyHandler);
            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new PythonExecutionException("Request failed with status code: " + response.statusCode());
            }
            return response.body();
        } catch (Exception e) {
            throw new PythonHttpRequestSendingException(e);
        }
    }
}
