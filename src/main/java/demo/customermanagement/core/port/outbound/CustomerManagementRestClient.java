package demo.customermanagement.core.port.outbound;


import demo.customermanagement.core.dto.customerdetails.request.CreateCustomerRequest;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;

public interface CustomerManagementRestClient {

    CustomerResponse createCustomer(
            final String clientMsgId,
            final String correlationId,
            final String timestamp,
            final String systemId,
            final String serviceId,
            final CreateCustomerRequest createCustomerRequest);
}
