package com.example.SpringBootREST3.controller;

import com.example.SpringBootREST3.exception.ErrorProperties;
import com.example.SpringBootREST3.model.OrderRequestForm;
import com.example.SpringBootREST3.model.OrderResponse;
import com.example.SpringBootREST3.model.PushMessageBody;
import com.example.SpringBootREST3.service.HomeService;
import com.example.SpringBootREST3.util.OrderServiceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/v3/rest")
@Tag(name = "someTag", description = "someDescription", externalDocs = @ExternalDocumentation(description = "Google", url = "https://www.google.com/"))
@Slf4j
public class NavController2 {

    private final HomeService homeService;

    private final OrderServiceUtil orderServiceUtil;

    private final ErrorProperties errorProperties;

    public NavController2(HomeService homeService, OrderServiceUtil orderServiceUtil, ErrorProperties errorProperties) {
        this.homeService = homeService;
        this.orderServiceUtil = orderServiceUtil;
        this.errorProperties = errorProperties;
    }


    //http://localhost:9100/v3/rest/getOrder3/Feb/red?parmRequestSource=web&parmAudienceType=external
    //Header - Actor = Microservice
    /*
        {
            "orderId": 100,
            "item": "iPhone",
            "itemType": "Phone",
            "invoiceType": 2
        }
     */
    @PostMapping(value = "/getOrder3/{month}/{colour}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> getOrder3(
            @PathVariable(value = "month") String month,
            @PathVariable(value = "colour") String colour,
            @RequestParam(value = "parmRequestSource") String parmRequestSource,
            @RequestParam(required = false, value = "parmAudienceType") String parmAudienceType,
            @RequestHeader(value = "Actor") String actor,
            @RequestBody OrderRequestForm orderRequestForm) {

        OrderResponse orderResponse = new OrderResponse();
        String item = orderRequestForm.getItem();

        if (!orderServiceUtil.allowedItems.contains(item)) {
            errorProperties.throwSPEException(ErrorProperties.ErrorCode.INVALID_ITEM, List.of(item));
        } else {
            orderResponse = homeService.getOrderResponse(orderResponse, orderRequestForm, parmAudienceType, parmRequestSource, month, actor);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("test", "test")
                .body(orderResponse);
    }

    //http://localhost:9100/v3/rest/getOrder4/Feb/red?parmRequestSource=web&parmAudienceType=external
    /*
    {
        "data": "ewogICAgIm9yZGVySWQiOiAxMDAsCiAgICAiaXRlbSI6ICJpUGhvbmUiLAogICAgIml0ZW1UeXBlIjogIlBob25lIiwKICAgICJpbnZvaWNlVHlwZSI6IDIKfQ=="
    }
     */

    @PostMapping(value = "/getOrder4/{month}/{colour}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> getOrder4(
            @PathVariable(value = "month") String month,
            @PathVariable(value = "colour") String colour,
            @RequestParam(value = "parmRequestSource") String parmRequestSource,
            @RequestParam(required = false, value = "parmAudienceType") String parmAudienceType,
            @RequestHeader(value = "Actor") String actor,
            @RequestBody PushMessageBody pushMessageBody) throws IOException {
        OrderRequestForm orderRequestForm = new ObjectMapper().readValue(Base64.getDecoder().decode(pushMessageBody.getData()), OrderRequestForm.class);
        OrderResponse orderResponse = new OrderResponse();
        String item = orderRequestForm.getItem();

        if (!orderServiceUtil.allowedItems.contains(item)) {
            errorProperties.throwSPEException(ErrorProperties.ErrorCode.INVALID_ITEM, List.of(item));
        } else {
            orderResponse = homeService.getOrderResponse(orderResponse, orderRequestForm, parmAudienceType, parmRequestSource, month, actor);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("test", "test")
                .body(orderResponse);
    }

}
