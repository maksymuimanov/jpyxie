package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.bind.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

@AutoConfiguration
public class PythonBindAutoConfiguration {
    @Bean
    public PythonTypeConverter pythonArrayConverter() {
        return new PythonArrayConverter();
    }

    @Bean
    public PythonTypeConverter pythonBigDecimalConverter() {
        return new PythonBigDecimalConverter();
    }

    @Bean
    public PythonTypeConverter pythonBigIntegerConverter() {
        return new PythonBigIntegerConverter();
    }

    @Bean
    public PythonTypeConverter pythonBooleanConverter() {
        return new PythonBooleanConverter();
    }

    @Bean
    public PythonTypeConverter pythonCalendarConverter() {
        return new PythonCalendarConverter();
    }

    @Bean
    public PythonTypeConverter pythonDateConverter() {
        return new PythonDateConverter();
    }

    @Bean
    public PythonTypeConverter pythonDictionaryConverter() {
        return new PythonDictionaryConverter();
    }

    @Bean
    public PythonTypeConverter pythonDurationConverter() {
        return new PythonDurationConverter();
    }

    @Bean
    public PythonTypeConverter pythonEnumDictionaryConverter() {
        return new PythonEnumDictionaryConverter();
    }

    @Bean
    public PythonTypeConverter pythonFloatConverter() {
        return new PythonFloatConverter();
    }

    @Bean
    public PythonTypeConverter pythonInstantConverter() {
        return new PythonInstantConverter();
    }

    @Bean
    public PythonTypeConverter pythonIntConverter() {
        return new PythonIntConverter();
    }

    @Bean
    public PythonTypeConverter pythonIterableConverter() {
        return new PythonIterableConverter();
    }

    @Bean
    public PythonTypeConverter pythonListConverter() {
        return new PythonListConverter();
    }

    @Bean
    public PythonTypeConverter pythonLocalDateConverter() {
        return new PythonLocalDateConverter();
    }

    @Bean
    public PythonTypeConverter pythonLocalDateTimeConverter() {
        return new PythonLocalDateTimeConverter();
    }

    @Bean
    public PythonTypeConverter pythonObjectDictionaryConverter() {
        return new PythonObjectDictionaryConverter();
    }

    @Bean
    public PythonTypeConverter pythonOffsetDateTimeConverter() {
        return new PythonOffsetDateTimeConverter();
    }

    @Bean
    public PythonTypeConverter pythonOptionalConverter() {
        return new PythonOptionalConverter();
    }

    @Bean
    public PythonTypeConverter pythonPeriodConverter() {
        return new PythonPeriodConverter();
    }

    @Bean
    public PythonTypeConverter pythonQueueConverter() {
        return new PythonQueueConverter();
    }

    @Bean
    public PythonTypeConverter pythonSetConverter() {
        return new PythonSetConverter();
    }

    @Bean
    public PythonTypeConverter pythonStringConverter() {
        return new PythonStringConverter();
    }

    @Bean
    public PythonTypeConverter pythonZonedDateTimeConverter() {
        return new PythonZonedDateTimeConverter();
    }

    @Bean
    public PythonTypeConverter pythonZoneIdConverter() {
        return new PythonZoneIdConverter();
    }

    @Bean
    @ConditionalOnMissingBean(PythonSerializer.class)
    public PythonSerializer dictionaryPythonSerializer(List<PythonTypeConverter> typeConverters) {
        return new DictionaryPythonSerializer(typeConverters);
    }
}
