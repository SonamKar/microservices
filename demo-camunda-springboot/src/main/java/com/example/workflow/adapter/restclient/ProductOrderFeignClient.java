package com.example.workflow.adapter.restclient;

import com.example.workflow.model.restresponse.RestOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    url = "${product-order-ms.url}",
    name = "product-order-ms"
)
public interface ProductOrderFeignClient {
  @PostMapping("/createOrder")
  String createProductOrder(@RequestBody RestOrderResponse orderResponse);
}
