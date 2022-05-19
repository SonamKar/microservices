package demo.customermanagement.core.port.inbound;


import demo.customermanagement.core.dto.request.Customer_Create;
import demo.customermanagement.core.dto.response.customerdetails.Customer;

public interface CreateCustomerPort {

    Customer createCustomerDetails(final Customer_Create createCustomer, final String correlationId, final String source);
}
