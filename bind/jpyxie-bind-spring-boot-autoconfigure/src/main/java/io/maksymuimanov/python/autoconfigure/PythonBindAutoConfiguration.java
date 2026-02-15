package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.bind.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@AutoConfiguration
@EnableConfigurationProperties(PythonBindProperties.class)
public class PythonBindAutoConfiguration {
    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "json")
    @ConditionalOnMissingBean(PythonSerializer.class)
    public PythonSerializer jsonPythonSerializer(ObjectMapper objectMapper) {
        return new JsonPythonSerializer(objectMapper);
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonArrayConverter() {
        return new PythonArrayConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonBigDecimalConverter() {
        return new PythonBigDecimalConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonBigIntegerConverter() {
        return new PythonBigIntegerConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonBooleanConverter() {
        return new PythonBooleanConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonCalendarConverter() {
        return new PythonCalendarConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonDateConverter() {
        return new PythonDateConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonDictionaryConverter() {
        return new PythonDictionaryConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonDurationConverter() {
        return new PythonDurationConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonEnumDictionaryConverter() {
        return new PythonEnumDictionaryConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonFloatConverter() {
        return new PythonFloatConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonInstantConverter() {
        return new PythonInstantConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonIntConverter() {
        return new PythonIntConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonIterableConverter() {
        return new PythonIterableConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonListConverter() {
        return new PythonListConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonLocalDateConverter() {
        return new PythonLocalDateConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonLocalDateTimeConverter() {
        return new PythonLocalDateTimeConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonObjectDictionaryConverter() {
        return new PythonObjectDictionaryConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonOffsetDateTimeConverter() {
        return new PythonOffsetDateTimeConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonOptionalConverter() {
        return new PythonOptionalConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonPeriodConverter() {
        return new PythonPeriodConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonQueueConverter() {
        return new PythonQueueConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonSetConverter() {
        return new PythonSetConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonStringConverter() {
        return new PythonStringConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonZonedDateTimeConverter() {
        return new PythonZonedDateTimeConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    public PythonTypeConverter pythonZoneIdConverter() {
        return new PythonZoneIdConverter();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.python.bind.type", havingValue = "dictionary")
    @ConditionalOnMissingBean(PythonSerializer.class)
    public PythonSerializer dictionaryPythonSerializer(List<PythonTypeConverter> typeConverters) {
        return new DictionaryPythonSerializer(typeConverters);
    }
}
