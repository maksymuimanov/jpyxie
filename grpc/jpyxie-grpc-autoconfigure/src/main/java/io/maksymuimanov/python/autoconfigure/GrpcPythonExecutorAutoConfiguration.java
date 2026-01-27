package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.maksymuimanov.python.bind.GrpcPythonDeserializer;
import io.maksymuimanov.python.bind.PythonDeserializer;
import io.maksymuimanov.python.executor.GrpcPythonExecutor;
import io.maksymuimanov.python.executor.PythonExecutor;
import io.maksymuimanov.python.library.GrpcPipManager;
import io.maksymuimanov.python.library.PipManager;
import io.maksymuimanov.python.proto.GrpcPythonResponse;
import io.maksymuimanov.python.proto.PythonGrpcServiceGrpc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.boot.jackson.JsonMixinModule;
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
@AutoConfiguration(beforeName = GrpcPythonExecutorAutoConfiguration.MAIN_PIP_AUTO_CONFIGURATION_REFERENCE)
@EnableConfigurationProperties(GrpcPythonExecutorProperties.class)
public class GrpcPythonExecutorAutoConfiguration {
    protected static final String MAIN_PIP_AUTO_CONFIGURATION_REFERENCE = "io.maksymuimanov.python.autoconfigure.PipAutoConfiguration";

    /**
     * HTTP header name used for passing the token in gRPC metadata.
     * <p>This header is attached to every outgoing gRPC request.</p>
     */
    public static final String TOKEN_KEY = "x-token";

    @Bean
    public ObjectMapper pythonObjectMapper() {
        return JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .addModule(new JavaTimeModule())
                .addModule(new JsonComponentModule())
                .addModule(new JsonMixinModule())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(PythonDeserializer.class)
    public PythonDeserializer<GrpcPythonResponse> pythonDeserializer(@Qualifier("pythonObjectMapper") ObjectMapper objectMapper) {
        return new GrpcPythonDeserializer(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub.class)
    public PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub(GrpcPythonServerConnectionDetails connectionDetails, GrpcChannelFactory channels) {
        ManagedChannel channel = channels.createChannel(connectionDetails.getUri());
        Metadata headers = new Metadata();
        var marshaller = Metadata.ASCII_STRING_MARSHALLER;
        Metadata.Key<String> tokenKey = Metadata.Key.of(TOKEN_KEY, marshaller);
        headers.put(tokenKey, connectionDetails.getToken());
        return PythonGrpcServiceGrpc.newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers));
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor grpcPythonExecutor(PythonDeserializer<GrpcPythonResponse> pythonDeserializer,
                                             PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub) {
        return new GrpcPythonExecutor(pythonDeserializer, stub);
    }

    /**
     * Creates {@link GrpcPythonServerConnectionDetails} for gRPC Python execution from
     * {@link GrpcPythonExecutorProperties}.
     *
     * <p>Activated when:
     * <ul>
     *   <li>No other {@link GrpcPythonServerConnectionDetails} bean is present</li>
     * </ul>
     *
     * @param properties non-null {@link GrpcPythonExecutorProperties} containing gRPC configuration
     * @return never {@code null}, immutable connection details instance
     */
    @Bean
    @ConditionalOnMissingBean(GrpcPythonServerConnectionDetails.class)
    public GrpcPythonServerConnectionDetails grpcConnectionDetails(GrpcPythonExecutorProperties properties) {
        return GrpcPythonServerConnectionDetails.of(properties.getToken(), properties.getUri());
    }

    @Bean
    @ConditionalOnMissingBean(PipManager.class)
    public PipManager pipManager(PythonGrpcServiceGrpc.PythonGrpcServiceBlockingStub stub) {
        return new GrpcPipManager(stub);
    }
}