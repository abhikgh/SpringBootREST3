package com.example.SpringBootREST3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
        "orderId",
        "location",
        "invoiceType",
        "orderItems"
})
@NoArgsConstructor
public class OrderRequestForm {

    @JsonProperty("orderId")
    @NotNull(message = "Order Id should not be not null") //for Integer
    @Digits(integer = 3, fraction = 0, message = "Invalid Order ID")
    @Positive
    private Integer orderId;

    @JsonProperty("item")
    @NotBlank(message = "Item is required") //for String
    @Size(min = 2, max = 20)
    private String item;

    @JsonProperty("itemType")
    @NotBlank(message = "itemType is required") //for String
    @Size(min = 2, max = 20)
    private String itemType;

    @JsonProperty("invoiceType")
    @NotNull(message = "Invoice type should not be not null") //for Integer
    @Positive(message = "Invoice type should not be negative")
    @Max(900)
    @Min(2)
    private Integer invoiceType;


}
