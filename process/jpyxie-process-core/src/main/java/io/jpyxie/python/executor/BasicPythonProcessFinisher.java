package io.jpyxie.python.executor;

import io.jpyxie.python.exception.PythonProcessFinishException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicPythonProcessFinisher implements ProcessFinisher {
    @Override
    public void finish(Process process) {
        try {
            int exitCode = process.exitValue();
            if (exitCode == 0) {
                log.info("Python script is executed with code: {}", exitCode);
            } else {
                PythonProcessFinishException exception = new PythonProcessFinishException("Python script is executed with code: " + exitCode);
                log.error(exception.getMessage(), exception);
                throw exception;
            }
            process.destroy();
        } catch (Exception e) {
            throw new PythonProcessFinishException(e);
        }
    }
}