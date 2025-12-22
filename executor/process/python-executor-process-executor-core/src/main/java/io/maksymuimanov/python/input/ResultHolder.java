package io.maksymuimanov.python.input;

import java.util.Map;

public interface ResultHolder<R> {
    R getResult(String fieldName);

    Map<String, R> getResultMap();
}
