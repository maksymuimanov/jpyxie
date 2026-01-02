package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.maksymuimanov.python.bind.JsonPythonDeserializer;
import io.maksymuimanov.python.bind.PythonDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.boot.jackson.JsonMixinModule;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(JsonPythonProperties.class)
public class JsonPythonAutoConfiguration {
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
    public PythonDeserializer<String> jsonPythonDeserializer(@Qualifier("pythonObjectMapper") ObjectMapper objectMapper,
                                                            JsonPythonProperties properties) {
        return new JsonPythonDeserializer(objectMapper, properties.isCached());
    }
}