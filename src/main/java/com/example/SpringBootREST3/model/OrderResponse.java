package com.example.SpringBootREST3.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {

	@JsonProperty
	private Integer orderId;
	@JsonProperty
	private String item;
	@JsonProperty
	private String itemType;
	@JsonProperty
	private Integer invoiceType;
	@JsonProperty
	private String greetMessage="Thanks for ordering";
	@JsonProperty
	private String parmRequestSource;
	@JsonProperty
	private String parmAudienceType;
	@JsonProperty
	private String month;
	@JsonProperty
	private String actor;
	@JsonProperty
	private String businessUnit;

}
