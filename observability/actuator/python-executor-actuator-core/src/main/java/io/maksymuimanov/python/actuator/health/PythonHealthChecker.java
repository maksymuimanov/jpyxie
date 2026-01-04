package io.maksymuimanov.python.actuator.health;

import org.springframework.boot.actuate.health.Health;

public interface PythonHealthChecker {
    String PING_SCRIPT_KEY = "Ping Script";
    String PING_SCRIPT = "print('Pinged Python for Health Indicator!')";
    String ERROR_MESSAGE_KEY = "Error Message";

    void check();

    default Health createHealthUp() {
        return Health.up()
                .withDetail(PING_SCRIPT_KEY, PING_SCRIPT)
                .build();
    }

    default Health createHealthDown(Exception ex) {
        return Health.down(ex)
                .withDetail(PING_SCRIPT_KEY, PING_SCRIPT)
                .withDetail(ERROR_MESSAGE_KEY, ex.getMessage())
                .build();
    }
}
