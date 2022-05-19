package demo.customermanagement.core.port.inbound;

import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;
import demo.customermanagement.core.dto.kafka.consumedetails.customerCreateEvent;


public interface CustomerManagementPort {

  CustomerResponse createCustomer(
      final customerCreateEvent customerCreateEvent);
}
