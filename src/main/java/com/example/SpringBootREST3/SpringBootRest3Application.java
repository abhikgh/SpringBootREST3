package com.example.SpringBootREST3;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EncryptablePropertySource("classpath:errorMessages_en.properties")
public class SpringBootRest3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRest3Application.class, args);
	}

}
