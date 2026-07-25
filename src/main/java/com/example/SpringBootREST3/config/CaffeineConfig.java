package com.example.SpringBootREST3.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffeineConfig {

    public static final String MOVIE_CACHE = "movieCache";

    @Bean(name = "caffeine")
    public @NonNull Caffeine<Object, Object> caffeine(){
        return Caffeine
                .newBuilder()
                .initialCapacity(100)
                .maximumSize(2000)
                .expireAfterWrite(1, TimeUnit.HOURS);
    }

    @Bean
    public CaffeineCacheManager caffeineCacheManager(@Qualifier("caffeine") Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        caffeineCacheManager.setAllowNullValues(false);
        caffeineCacheManager.setCacheNames(List.of(MOVIE_CACHE));
        return caffeineCacheManager;
    }


}
