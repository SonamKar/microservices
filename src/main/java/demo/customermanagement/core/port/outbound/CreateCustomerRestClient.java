package demo.customermanagement.core.port.outbound;

import demo.customermanagement.core.dto.restrequest.createcustomer.CreateCustomerRequest;
import demo.customermanagement.core.dto.restresponse.createcustomer.CreateCustomerResponse;

public interface CreateCustomerRestClient {

    CreateCustomerResponse createCustomer(final CreateCustomerRequest createCustomerRequest);
}
