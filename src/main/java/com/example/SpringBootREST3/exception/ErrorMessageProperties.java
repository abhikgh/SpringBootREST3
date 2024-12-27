package com.example.SpringBootREST3.exception;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "error.message")
@Data
@Validated
public class ErrorMessageProperties {

    @Valid
    private final ErrorMessageProperties.Validation validation = new ErrorMessageProperties.Validation();

    @Valid
    private final ErrorMessageProperties.DataConnection dataConnection = new ErrorMessageProperties.DataConnection();

    @Data
    public static class Validation {
        @NotNull
        private String itemMismatch;
        @NotNull
        private String itemTypeMismatch;
    }

    @Data
    public static class DataConnection {
        @NotNull
        private String itemAndItemTypeMismatch;

    }
}
