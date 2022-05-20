package com.example.workflow.adapter.restclient;

import com.example.workflow.model.restresponse.RestOrderResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductOrderRestClient {
   String createProductOrder(@RequestBody RestOrderResponse orderResponse);
  }
