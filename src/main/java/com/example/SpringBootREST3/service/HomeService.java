package com.example.SpringBootREST3.service;

import com.example.SpringBootREST3.model.OrderRequestForm;
import com.example.SpringBootREST3.model.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HomeService {

    private static final Logger logger  = LoggerFactory.getLogger(HomeService.class);

    @Value("${BUSINESS_UNIT_LIST}")
    private String businessUnit;


    @Value("${value123}")
    private String value123;

    public int validateTestValue(){
        if(value123.equalsIgnoreCase("100"))
            return 100;
        else if(value123.equalsIgnoreCase("200"))
            return 200;
        else
            return 300;

    }

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

    public OrderResponse getOrderResponse(OrderResponse orderResponse, OrderRequestForm orderRequestForm, String parmAudienceType, String parmRequestSource, String month, String actor) {
        orderResponse.setOrderId(orderRequestForm.getOrderId());
        orderResponse.setItem(orderRequestForm.getItem());
        orderResponse.setItemType(orderRequestForm.getItemType());
        orderResponse.setInvoiceType(orderRequestForm.getInvoiceType());
        orderResponse.setParmRequestSource(parmRequestSource);
        orderResponse.setParmAudienceType(parmAudienceType);
        orderResponse.setMonth(month);
        orderResponse.setActor(actor);
        orderResponse.setBusinessUnit(businessUnit);
        return orderResponse;
    }
}
