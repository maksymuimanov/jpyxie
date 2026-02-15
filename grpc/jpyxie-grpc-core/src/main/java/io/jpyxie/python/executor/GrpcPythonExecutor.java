package io.jpyxie.python.executor;

import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.exception.PythonExecutionException;
import io.jpyxie.python.processor.PythonResultMap;
import io.jpyxie.python.proto.GrpcPythonRequest;
import io.jpyxie.python.proto.GrpcPythonResponse;
import io.jpyxie.python.proto.PythonGrpcServiceGrpc;
import io.jpyxie.python.script.PythonScript;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcPythonExecutor extends AbstractPythonExecutor<GrpcPythonResponse> {
    private final PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub;

    public GrpcPythonExecutor(PythonDeserializer<GrpcPythonResponse> pythonDeserializer, PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub) {
        super(pythonDeserializer);
        this.stub = stub;
    }

    @Override
    public PythonResultMap execute(PythonScript script, PythonResultSpec resultSpec) {
        try {
            String scriptBody = script.toPythonString();
            GrpcPythonRequest request = GrpcPythonRequest.newBuilder()
                    .setScript(scriptBody)
                    .addAllFieldNames(resultSpec.stream()
                            .map(PythonResultRequirement::name)
                            .toList())
                    .build();
            GrpcPythonResponse response = stub.sendCode(request);
            return this.createResultMap(resultSpec, response);
        } catch (Exception e) {
            throw new PythonExecutionException(e);
        }
    }
}
