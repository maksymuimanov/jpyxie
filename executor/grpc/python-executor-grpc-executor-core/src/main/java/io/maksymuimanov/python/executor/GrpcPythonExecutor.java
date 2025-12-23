package io.maksymuimanov.python.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonExecutionException;
import io.maksymuimanov.python.proto.PythonGrpcRequest;
import io.maksymuimanov.python.proto.PythonGrpcResponse;
import io.maksymuimanov.python.proto.PythonGrpcServiceGrpc;
import io.maksymuimanov.python.script.PythonScript;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link PythonExecutor} interface that executes Python scripts via a gRPC endpoint.
 * <p>
 * This class uses a gRPC blocking stub to send Python code for execution on a remote Python service.
 * It handles communication with the gRPC server, processes the response, and converts the
 * returned JSON body into the specified Java type.
 * <p>
 * This executor abstracts the complexity of starting and managing Python processes,
 * relying on the gRPC service to execute scripts and return results.
 * <p>
 * Usage example:
 * <pre>{@code
 * PythonExecutor executor = new GrpcPythonExecutor(stub, objectMapper);
 * String script = "print('Hello, World!')";
 * String body = executor.execute(script, String.class);
 * }</pre>
 *
 * @see PythonExecutor
 * @see PythonGrpcRequest
 * @see PythonGrpcResponse
 * @see PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub
 * @author w4t3rcs
 * @since 1.0.0
 */
@Slf4j
public class GrpcPythonExecutor extends AbstractPythonExecutor<PythonGrpcResponse> {
    private final PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub;
    private final ObjectMapper objectMapper;

    public GrpcPythonExecutor(PythonResultFieldNameProvider resultFieldProvider, PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub, ObjectMapper objectMapper) {
        super(resultFieldProvider);
        this.stub = stub;
        this.objectMapper = objectMapper;
    }

    @Override
    @Nullable
    public <R> R execute(PythonScript script, PythonResultDescription<R> resultDescription) {
        try {
            String scriptBody = script.toPythonString();
            PythonGrpcRequest request = PythonGrpcRequest.newBuilder()
                    .setScript(scriptBody)
                    .addFieldNames(resultDescription.fieldName())
                    .build();
            PythonGrpcResponse response = stub.sendCode(request);
            return this.getResult(resultDescription, response);
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
            PythonGrpcRequest request = PythonGrpcRequest.newBuilder()
                    .setScript(scriptBody)
                    .addAllFieldNames(fieldNames)
                    .build();
            PythonGrpcResponse response = stub.sendCode(request);
            return this.getResultMap(resultDescriptions, response);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }

    @Override
    @Nullable
    protected <R> R getResult(PythonResultDescription<R> resultDescription, PythonGrpcResponse resultContainer) {
        return resultDescription.getValue((type, fieldName) -> {
            String resultJson = resultContainer.getFieldsOrThrow(fieldName);
            return this.parseJson(resultJson, type);
        });
    }

    private <R> R parseJson(String json, Class<R> type) {
        try {
            return this.objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new PythonExecutionException(e);
        }
    }
}
