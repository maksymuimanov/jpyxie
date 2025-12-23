package io.maksymuimanov.python.executor;

import io.maksymuimanov.python.script.PythonScript;
import org.jspecify.annotations.Nullable;

import java.util.Map;

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
    @Nullable
    <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription);

    Map<String, @Nullable Object> execute(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions);
}

