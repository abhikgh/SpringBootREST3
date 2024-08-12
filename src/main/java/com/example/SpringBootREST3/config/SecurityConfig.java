package com.example.SpringBootREST3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Value("${application.spring.endpoints.exclude}")
   private String[] applicationApisToBeExcluded;

    @Value("${service.endpoints.exclude:}")
    private String[] serviceApisToBeExcluded;

    private String[] allApisToBeExcluded(){
        return Stream.of(applicationApisToBeExcluded, serviceApisToBeExcluded)
                .flatMap(Stream::of)
                .toArray(String[]::new);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.requestMatchers(allApisToBeExcluded()).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
