package com.example.SpringBootREST3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HomeService {

    private static final Logger logger  = LoggerFactory.getLogger(HomeService.class);

    public String getResponse(){
        //Adding sleep
        int sleepTime = 250 ; //new Random().nextInt(1000); -- Uncomment the line if you want to add random delay

        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            logger.error( e.getMessage());
        }
        return "Current Thread Name: " + Thread.currentThread().toString();
    }
}
