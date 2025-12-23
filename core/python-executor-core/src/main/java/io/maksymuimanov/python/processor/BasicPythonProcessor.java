package io.maksymuimanov.python.processor;

import io.maksymuimanov.python.exception.PythonProcessionException;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.executor.PythonResultDescription;
import io.maksymuimanov.python.executor.PythonResultFieldNameProvider;
import io.maksymuimanov.python.file.PythonFileReader;
import io.maksymuimanov.python.resolver.PythonResolverHolder;
import io.maksymuimanov.python.response.PythonResponse;
import io.maksymuimanov.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Default implementation of {@link PythonProcessor} that provides the basic
 * processing workflow for executing Python scripts with optional argument resolution.
 *
 * <p>This implementation integrates three main components:
 * <ul>
 *     <li>{@link PythonFileReader} – detects if the given script is a file and, if so,
 *         reads its contents.</li>
 *     <li>{@link PythonResolverHolder} – applies all registered resolvers to the script
 *         (e.g., resolves placeholders or SpEL expressions) using the provided arguments.</li>
 *     <li>{@link PythonExecutor} – executes the fully resolved Python script and
 *         converts the body to the requested Java type.</li>
 * </ul>
 *
 * <p><b>Workflow:</b>
 * <ol>
 *     <li>If {@code script} is a Python file path, load its contents.</li>
 *     <li>Apply argument-based resolution to the script text.</li>
 *     <li>Execute the resolved script and return the execution body.</li>
 * </ol>
 *
 * <p>Example usage:
 * <pre>{@code
 * BasicPythonProcessor processor = new BasicPythonProcessor(fileHandler, executor, resolverHolder);
 * String body = processor.process("print('Hello')", String.class, Map.of());
 * }</pre>
 *
 * @see PythonProcessor
 * @see PythonExecutor
 * @see PythonFileReader
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class BasicPythonProcessor implements PythonProcessor {
    private final PythonFileReader pythonFileReader;
    private final PythonExecutor pythonExecutor;
    private final PythonResolverHolder pythonResolverHolder;
    private final PythonResultFieldNameProvider resultFieldNameProvider;

    @Override
    public <R> PythonResponse<R> process(PythonScript script, Class<R> resultClass, Map<String, Object> arguments) {
        PythonResultDescription<R> resultDescription = new PythonResultDescription<>(resultClass, resultFieldNameProvider.get());
        return this.process(script, resultDescription, arguments);
    }

    @Override
    public <R> PythonResponse<R> process(PythonScript script, PythonResultDescription<R> resultDescription, Map<String, Object> arguments) {
        try {
            if (script.isFile()) pythonFileReader.readScript(script);
            pythonResolverHolder.resolveAll(script, arguments);
            String name = resultDescription.fieldName();
            R result = pythonExecutor.execute(script, resultDescription);
            return PythonResponse.of(name, result);
        } catch (Exception e) {
            throw new PythonProcessionException(e);
        }
    }

    @Override
    public List<PythonResponse<?>> processAll(PythonScript script, Iterable<PythonResultDescription<?>> resultDescriptions, Map<String, Object> arguments) {
        try {
            if (script.isFile()) pythonFileReader.readScript(script);
            pythonResolverHolder.resolveAll(script, arguments);
            Map<String, @Nullable Object> resultMap = pythonExecutor.execute(script, resultDescriptions);
            return PythonResponse.allOf(resultMap);
        } catch (Exception e) {
            throw new PythonProcessionException(e);
        }
    }
}
