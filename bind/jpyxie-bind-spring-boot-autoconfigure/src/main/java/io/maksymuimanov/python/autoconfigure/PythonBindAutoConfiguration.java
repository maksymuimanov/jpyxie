package io.maksymuimanov.python.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.bind.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import java.util.List;

@AutoConfiguration
@EnableConfigurationProperties(PythonBindProperties.class)
public class PythonBindAutoConfiguration {
    @Bean
    @Conditional(PythonBindJsonCondition.class)
    @ConditionalOnMissingBean(PythonSerializer.class)
    public PythonSerializer jsonPythonSerializer(ObjectMapper objectMapper) {
        return new JsonPythonSerializer(objectMapper);
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonArrayConverter() {
        return new PythonArrayConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonBigDecimalConverter() {
        return new PythonBigDecimalConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonBigIntegerConverter() {
        return new PythonBigIntegerConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonBooleanConverter() {
        return new PythonBooleanConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonCalendarConverter() {
        return new PythonCalendarConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonDateConverter() {
        return new PythonDateConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonDictionaryConverter() {
        return new PythonDictionaryConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonDurationConverter() {
        return new PythonDurationConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonEnumDictionaryConverter() {
        return new PythonEnumDictionaryConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonFloatConverter() {
        return new PythonFloatConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonInstantConverter() {
        return new PythonInstantConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonIntConverter() {
        return new PythonIntConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonIterableConverter() {
        return new PythonIterableConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonListConverter() {
        return new PythonListConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonLocalDateConverter() {
        return new PythonLocalDateConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonLocalDateTimeConverter() {
        return new PythonLocalDateTimeConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonObjectDictionaryConverter() {
        return new PythonObjectDictionaryConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonOffsetDateTimeConverter() {
        return new PythonOffsetDateTimeConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonOptionalConverter() {
        return new PythonOptionalConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonPeriodConverter() {
        return new PythonPeriodConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonQueueConverter() {
        return new PythonQueueConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonSetConverter() {
        return new PythonSetConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonStringConverter() {
        return new PythonStringConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonZonedDateTimeConverter() {
        return new PythonZonedDateTimeConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    public PythonTypeConverter pythonZoneIdConverter() {
        return new PythonZoneIdConverter();
    }

    @Bean
    @Conditional(PythonBindDictionaryCondition.class)
    @ConditionalOnMissingBean(PythonSerializer.class)
    public PythonSerializer dictionaryPythonSerializer(List<PythonTypeConverter> typeConverters) {
        return new DictionaryPythonSerializer(typeConverters);
    }
}
