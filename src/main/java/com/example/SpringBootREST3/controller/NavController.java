package com.example.SpringBootREST3.controller;

import com.example.SpringBootREST3.model.Person;
import com.example.SpringBootREST3.service.HomeService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v3/rest")
public class NavController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> hello() {

        //Counter metric
        Counter counter = Counter.builder("hello")
                .description("gets number of requests to /hello endpoint")
                .register(meterRegistry);
        counter.increment();

        return ResponseEntity.status(HttpStatus.OK)
                .body("Hello World");
    }

    @GetMapping(value = "/hello2", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> hello2() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Hello World 2");
    }

    @PostMapping(value = "/helloPost", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> helloPost(@RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(person.getFirstName().concat("----").concat(person.getLastName()));
    }

    @GetMapping("/home")
    public String getResponse(){
        return homeService.getResponse();
    }
}
