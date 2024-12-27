package com.example.SpringBootREST3.exception;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "error.infomessage")
@Data
public class ErrorInfoMessageProperties {

    @Valid
    private final ErrorInfoMessageProperties.Validation validation = new ErrorInfoMessageProperties.Validation();

    @Valid
    private final ErrorInfoMessageProperties.DataConnection dataConnection = new ErrorInfoMessageProperties.DataConnection();

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
