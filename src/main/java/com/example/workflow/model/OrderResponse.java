package com.example.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
  @JsonProperty("order")
  Order order;

  @JsonProperty("paymentDetails")
  String paymentDetails;

  public OrderResponse(){

  }
  public OrderResponse(Order order,String paymentDetails){
    this.order=order;
    this.paymentDetails=paymentDetails;
  }
}
