package io.maksymuimanov.python.response;

import org.jspecify.annotations.Nullable;

/**
 * Response wrapper for Python script execution result.
 *
 * @param <R> the type of the execution result body
 * @param body the result returned from executing the Python script
 */
public record PythonExecutionResponse<R>(@Nullable R body) {
}
