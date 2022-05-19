package demo.customermanagement.adapter.outbound.restclient;

import demo.customermanagement.config.CreateCustomerFeignBasicAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import demo.customermanagement.core.dto.customerdetails.request.CreateCustomerRequest;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;

@FeignClient(
        url = "${create-customer.url}",
        name = "customer-management",
        configuration = CreateCustomerFeignBasicAuthConfig.class)
public interface CustomerManagementFeignClient {

  @PostMapping("/customers")
  CustomerResponse createCustomer(
          @RequestHeader("ClientMsgRef ") final String clientMsgId,
          @RequestHeader("CorrelationRef") final String correlationId,
          @RequestHeader("Timestamp") final String timestamp,
          @RequestHeader("SystemId") final String systemId,
          @RequestHeader("ServiceId") final String serviceId,
          @RequestBody final CreateCustomerRequest createCustomerRequest);
}
