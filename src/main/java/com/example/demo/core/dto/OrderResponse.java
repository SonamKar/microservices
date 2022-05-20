package com.example.demo.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
  @JsonProperty("orderid")
  String orderid;

  @JsonProperty("ordername")
  String ordername;

  @JsonProperty("paymentDetails")
  String paymentDetails;
}
