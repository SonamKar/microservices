package com.example.workflow.adapter.restclient;

import com.example.workflow.model.restresponse.RestOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Component
public class ProductOrderRestClientImpl implements ProductOrderRestClient{
  private final ProductOrderFeignClient productOrderFeignClient;
  public String createProductOrder(@RequestBody RestOrderResponse orderResponse){
    return productOrderFeignClient.createProductOrder(orderResponse);
  }

}
