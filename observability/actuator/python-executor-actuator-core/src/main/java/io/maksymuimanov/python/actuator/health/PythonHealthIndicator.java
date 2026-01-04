package io.maksymuimanov.python.actuator.health;

import io.maksymuimanov.python.processor.PythonProcessor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class PythonHealthIndicator extends AbstractPythonHealthChecker implements HealthIndicator {
    public PythonHealthIndicator(PythonProcessor pythonProcessor) {
        super(pythonProcessor);
    }

    @Override
    public Health health() {
        try {
            this.check();
            return this.createHealthUp();
        } catch (Exception ex) {
            return this.createHealthDown(ex);
        }
    }
}
