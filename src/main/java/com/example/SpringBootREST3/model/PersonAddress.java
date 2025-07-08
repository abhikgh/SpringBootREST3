package com.example.SpringBootREST3.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PersonAddress {

    @Valid
    @NotEmpty
    private Map<String, String> addressMap = new HashMap<>();
}
