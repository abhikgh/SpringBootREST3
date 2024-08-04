package com.example.SpringBootREST3.config;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfig {

    //Tomcat Protocol Handler is responsible for handling incoming requests in a Spring Boot application
    //TomcatProtocolHandlerCustomizer configures the Executor
    //Executor is responsible for executing tasks, like handling incoming requests.
    @Bean
    TomcatProtocolHandlerCustomizer<?> tomcatProtocolHandlerCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }
}
