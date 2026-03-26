package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.executor.RestPythonExecutor;
import io.jpyxie.python.http.BasicPythonServerRequestSender;
import io.jpyxie.python.library.RestPythonLibraryManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("spring.python.rest")
public class RestPythonProperties {
    private String host = BasicPythonServerRequestSender.DEFAULT_HOST;
    private int port = BasicPythonServerRequestSender.DEFAULT_PORT;
    private String token = BasicPythonServerRequestSender.DEFAULT_TOKEN;
    private String scriptEndpoint = RestPythonExecutor.DEFAULT_ENDPOINT;
    private String executeUri = this.host + ":" + this.port + this.scriptEndpoint;
    private String pipEndpoint = RestPythonLibraryManager.DEFAULT_ENDPOINT;
    private String pipUri = this.host + ":" + this.port + this.pipEndpoint;
}
