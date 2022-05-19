package demo.customermanagement.adapter.outbound.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import demo.customermanagement.config.CreateCustomerFeignBasicAuthConfig;
import demo.customermanagement.core.dto.restrequest.createcustomer.CreateCustomerRequest;
import demo.customermanagement.core.dto.restresponse.createcustomer.CreateCustomerResponse;

@FeignClient(
    url = "${create-customer.url}",
    name = "create-customer",
    configuration = CreateCustomerFeignBasicAuthConfig.class)
public interface CreateCustomerFeignClient {

  @PostMapping("/createCustomer")
  CreateCustomerResponse createCustomer(
      @RequestBody final CreateCustomerRequest createCustomerRequest);
}
