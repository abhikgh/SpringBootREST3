package com.example.SpringBootREST3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    @JsonProperty
    @NotBlank
    @Size(min = 5, message = "First name must have at-least 5 chars")
    @Schema(description = "First name", example = "John")
    private String firstName;

    @JsonProperty
    @Schema(description = "Last name", example = "Doe")
    private String lastName;
}
