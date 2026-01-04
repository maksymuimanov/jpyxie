package io.maksymuimanov.python.actuator.info;

import java.util.List;

public record PythonInfo(String version,
                         String pipManagerName,
                         String fileReaderName,
                         List<String> resolverNames,
                         String executorName) {
}
