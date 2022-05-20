package com.example.workflow.model.restresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestOrderResponse {
  @JsonProperty("orderid")
  String orderid;

  @JsonProperty("ordername")
  String ordername;

  @JsonProperty("paymentDetails")
  String paymentDetails;
}
