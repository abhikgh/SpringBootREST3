package com.example.SpringBootREST3.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfig {

    //Tomcat Protocol Handler is responsible for handling incoming requests in a Spring Boot application
    //TomcatProtocolHandlerCustomizer configures the Executor
    //Executors.newVirtualThreadPerTaskExecutor() -
        // - executor service that creates a new Virtual Thread for each task submitted to it
    //Virtual Threads
    @Bean
    TomcatProtocolHandlerCustomizer<?> tomcatProtocolHandlerCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

    // Monitoring beans
    @Bean
    ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }

    // To have the @Observed support we need to register this aspect
    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
}
