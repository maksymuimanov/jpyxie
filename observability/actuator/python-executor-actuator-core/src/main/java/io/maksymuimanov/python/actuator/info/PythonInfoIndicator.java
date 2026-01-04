package io.maksymuimanov.python.actuator.info;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

@RequiredArgsConstructor
public class PythonInfoIndicator implements InfoContributor {
    private final PythonInfo pythonInfo;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("pythonInfo", pythonInfo);
    }
}
