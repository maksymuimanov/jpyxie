package io.maksymuimanov.python.http;

public interface PythonServerRequestSender {
    String CONTENT_TYPE_HEADER = "Content-Type";
    String JSON_CONTENT_TYPE = "application/json";
    String TOKEN_HEADER = "X-Token";

    String send(String uri, String token, String requestJson, String... headers);
}
