package io.maksymuimanov.python.actuator.health;

import io.maksymuimanov.python.processor.PythonProcessor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class PythonHealthIndicator extends AbstractHealthIndicator {
    public static final String PING_SCRIPT_KEY = "pingScript";
    public static final String PING_SCRIPT_VALUE = "print('Ping!')";
    private final PythonProcessor pythonProcessor;

    public PythonHealthIndicator(PythonProcessor pythonProcessor) {
        this.pythonProcessor = pythonProcessor;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up().withDetail(PING_SCRIPT_KEY, PING_SCRIPT_VALUE);
        this.pythonProcessor.process(PING_SCRIPT_VALUE);
    }
}
