package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.maksymuimanov.python.executor.GrpcPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.proto.PythonServiceGrpc;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;

/**
 * Central Spring Boot configuration for creating and wiring {@link PythonExecutor} beans.
 *
 * <p>This class declares {@link Bean} methods for remote execution via gRPC using {@link GrpcPythonExecutor}
 *
 * @see PythonExecutor
 * @see GrpcPythonExecutor
 * @see GrpcPythonExecutorProperties
 * @author w4t3rcs
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(GrpcPythonExecutorProperties.class)
public class GrpcPythonExecutorAutoConfiguration {
    /**
     * HTTP header name used for passing the token in gRPC metadata.
     * <p>This header is attached to every outgoing gRPC request.</p>
     */
    public static final String TOKEN_KEY = "x-token";

    /**
     * Creates a {@link GrpcPythonExecutor} bean for executing Python scripts via gRPC.
     *
     * <p>Activated when:
     * <ul>
     *   <li>No other {@link PythonExecutor} bean is present in the context</li>
     * </ul>
     *
     * @param stub non-null {@link PythonServiceGrpc.PythonServiceBlockingStub} for gRPC communication
     * @param objectMapper non-null {@link ObjectMapper} for JSON serialization/deserialization
     * @return never {@code null}, fully initialized {@link GrpcPythonExecutor} instance
     */
    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor grpcPythonExecutor(PythonServiceGrpc.PythonServiceBlockingStub stub, ObjectMapper objectMapper) {
        return new GrpcPythonExecutor(stub, objectMapper);
    }

    /**
     * Creates {@link PythonGrpcServerConnectionDetails} for gRPC Python execution from
     * {@link GrpcPythonExecutorProperties}.
     *
     * <p>Activated when:
     * <ul>
     *   <li>No other {@link PythonGrpcServerConnectionDetails} bean is present</li>
     * </ul>
     *
     * @param properties non-null {@link GrpcPythonExecutorProperties} containing gRPC configuration
     * @return never {@code null}, immutable connection details instance
     */
    @Bean
    @ConditionalOnMissingBean(PythonGrpcServerConnectionDetails.class)
    public PythonGrpcServerConnectionDetails grpcConnectionDetails(GrpcPythonExecutorProperties properties) {
        return PythonGrpcServerConnectionDetails.of(properties.getToken(), properties.getUri());
    }

    /**
     * Creates a {@link PythonServiceGrpc.PythonServiceBlockingStub} bean configured with:
     * <ul>
     *   <li>Managed channel targeting the Python gRPC server URI</li>
     *   <li>Authentication metadata containing username and token</li>
     * </ul>
     *
     * <p>The bean is only created if:</p>
     * <ul>
     *   <li>No other {@link PythonServiceGrpc.PythonServiceBlockingStub} bean exists in the context</li>
     * </ul>
     *
     * @param connectionDetails non-null connection configuration, including URI, username, and token
     * @param channels non-null {@link GrpcChannelFactory} for creating managed gRPC channels
     * @return non-null gRPC blocking stub ready for synchronous communication with the Python service
     */
    @Bean
    @ConditionalOnMissingBean(PythonServiceGrpc.PythonServiceBlockingStub.class)
    public PythonServiceGrpc.PythonServiceBlockingStub stub(PythonGrpcServerConnectionDetails connectionDetails, GrpcChannelFactory channels) {
        ManagedChannel channel = channels.createChannel(connectionDetails.getUri());
        Metadata headers = new Metadata();
        var marshaller = Metadata.ASCII_STRING_MARSHALLER;
        Metadata.Key<String> tokenKey = Metadata.Key.of(TOKEN_KEY, marshaller);
        headers.put(tokenKey, connectionDetails.getToken());
        return PythonServiceGrpc.newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers));
    }
}