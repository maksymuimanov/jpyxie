package io.jpyxie.demo;

import io.jpyxie.python.interpreter.PoolPythonInterpreterProvider;
import io.jpyxie.python.interpreter.PythonInterpreterFactory;
import io.jpyxie.python.interpreter.PythonInterpreterProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JPyxieJythonDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JPyxieJythonDemoApplication.class, args);
    }

    @Bean
    public PythonInterpreterProvider<?> interpreterProvider(PythonInterpreterFactory<?> factory) {
        return new PoolPythonInterpreterProvider<>(factory);
    }
}
