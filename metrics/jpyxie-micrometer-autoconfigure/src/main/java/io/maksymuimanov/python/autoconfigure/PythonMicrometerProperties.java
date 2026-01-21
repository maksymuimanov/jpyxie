package io.maksymuimanov.python.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("management.metrics.python")
public class PythonMicrometerProperties {
    /**
     * Percentiles to compute for Python-related Micrometer timers.
     */
    private double @Nullable [] percentiles = null;
}
