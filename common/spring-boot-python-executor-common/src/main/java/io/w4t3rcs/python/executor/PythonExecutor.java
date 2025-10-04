package io.w4t3rcs.python.executor;

import io.w4t3rcs.python.exception.PythonScriptExecutionException;
import io.w4t3rcs.python.response.PythonExecutionResponse;
import io.w4t3rcs.python.script.PythonScript;
import org.jspecify.annotations.Nullable;

/**
 * Defines the contract for executing Python scripts and mapping the execution body
 * to a Java object of the specified type.
 *
 * <p>Implementations of this interface are responsible for executing Python code
 * in a controlled environment (such as a sandbox, container, or embedded interpreter)
 * and converting the execution body to the requested Java type.
 * This interface is typically used by higher-level components
 * to integrate Python execution into Java applications.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * PythonExecutor executor = ...;
 * String output = executor.execute("print('Hello')", String.class);
 * }</pre>
 *
 * @author w4t3rcs
 * @since 1.0.0
 */
public interface PythonExecutor {
    /**
     * Executes the given Python script and converts the body to the specified Java type.
     *
     * <p>Implementations must execute the script in a safe and isolated context and perform type conversion to the given {@code resultClass}.
     * The execution order and environment are defined by the specific implementation.</p>
     *
     * @param <R> the expected body type
     * @param script non-{@code null} Python script to execute
     * @param resultClass the Java class representing the expected body type, may be {@code null} if the script produces no output
     * @throws PythonScriptExecutionException the exception must be thrown if something goes wrong with the script execution
     * @return the body of the script execution mapped to {@code resultClass}, may be {@code null} if the script produces no output
     */
    <R> PythonExecutionResponse<R> execute(PythonScript script, @Nullable Class<? extends R> resultClass);
}

