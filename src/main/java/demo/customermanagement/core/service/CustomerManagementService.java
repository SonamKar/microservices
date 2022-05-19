package demo.customermanagement.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import demo.customermanagement.core.port.inbound.CustomerManagementPort;
import demo.customermanagement.core.port.outbound.CustomerManagementRestClient;
import feign.RetryableException;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import demo.customermanagement.core.constant.Constants;
import demo.customermanagement.core.constant.MessageKeys;
import demo.customermanagement.core.dto.customerdetails.request.CreateCustomerRequest;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;
import demo.customermanagement.core.dto.kafka.consumedetails.customerCreateEvent;
import demo.customermanagement.core.logging.JacksonIgnoreAvroPropertiesMixIn;
import demo.customermanagement.core.transformer.CustomerManagementMapper;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerManagementService implements CustomerManagementPort {

    private static final String JSON_PROCESSING_ERROR =
            "Error while processing Json Response payload";
    private static final String NO_RESPONSE_PAYLOAD_FROM_CREATE_CUSTOMER_FEIGN =
            "Response payload not received from CreateCustomerFeign";

    private final CustomerManagementMapper customerManagementMapper;
    private final CustomerManagementRestClient customerManagementRestClient;
    private final MyExceptionBuilder exceptionBuilder;
    private final ObjectMapper objectMapper;

    @Override
    public CustomerResponse createCustomer(
            final customerCreateEvent customerCreateEvent) {

        try {
            log.info("inside CustomerManagementService class :: createCustomer method");
            if (customerCreateEvent != null) {
                log.info("customerCreateEvent is not null condition check ");
                log.info("Create customer request received :: {} ",
                        objectMapper.addMixIn(org.apache.avro.specific.SpecificRecord.class,
                                JacksonIgnoreAvroPropertiesMixIn.class).writeValueAsString(customerCreateEvent));

        /*
        mapping customerCreateEvent to CreateCustomerFeign request
         */
                CreateCustomerRequest createCustomerRequest =
                        customerManagementMapper.constructCreateCustomerRequest(customerCreateEvent);
                log.info("Mapped createCustomerRequest is :: {}",
                        objectMapper.writeValueAsString(createCustomerRequest));

      /*
      invoking CreateCustomerFeign
       */
                CustomerResponse customerResponse = null;
                if (createCustomerRequest != null) {
                    log.info("createCustomerRequest is null condition check ");
                    log.info("invoking CreateCustomerFeign for request :: {}", objectMapper.writeValueAsString(
                            createCustomerRequest));

                    customerResponse = callCreateCustomer(
                            Constants.SOURCE,
                            UUID.randomUUID().toString(),
                            generateTimeStamp(),
                            Constants.SYSTEM_ID,
                            Constants.SERVICE_ID,
                            createCustomerRequest);
                }
      /*
      getting response from CreateCustomerFeign
       */
                if (customerResponse != null) {
                    log.info("customerResponse is not null condition check ");
                    log.info("customerResponse received from CreateCustomerFeign :: {} ", objectMapper.writeValueAsString(
                            customerResponse));
                    return customerResponse;
                } else {
                    exceptionBuilder.throwResourceNotFoundException(
                            "Adapter Error",
                            MessageKeys.NO_RESPONSE_PAYLOAD_FROM_CREATE_CUSTOMER_FEIGN,
                            NO_RESPONSE_PAYLOAD_FROM_CREATE_CUSTOMER_FEIGN,
                            NO_RESPONSE_PAYLOAD_FROM_CREATE_CUSTOMER_FEIGN);
                }
            }
        }catch (JsonProcessingException ex) {
            exceptionBuilder.throwTechnicalException(
                    "Runtime Error",
                    "Runtime Error",
                    JSON_PROCESSING_ERROR,
                    ex.getLocalizedMessage());
        }
        return null;
    }

    /*
    Method for CreateCustomerFeign Feign Client call
     */
    private CustomerResponse callCreateCustomer(
            final String clientMsgId,
            final String correlationId,
            final String timestamp,
            final String systemId,
            final String serviceId,
            final CreateCustomerRequest createCustomerRequest) {
        try {
            log.info("CustomerManagementService class :: callCreateCustomer method");
            return customerManagementRestClient.createCustomer(
                    clientMsgId,
                    correlationId,
                    timestamp,
                    systemId,
                    serviceId,
                    createCustomerRequest);
        } catch (CallNotPermittedException | BulkheadFullException ex) {
            exceptionBuilder.throwTechnicalException(
                    "Adapter Error",
                    MessageKeys.ERROR_CONNECTING_CREATE_CUSTOMER_FEIGN,
                    "Error connecting to CreateCustomerFeign, please try after sometime",
                    ex.getMessage());
        } catch (RetryableException ex) {
            exceptionBuilder.throwTechnicalException(
                    "Adapter Error",
                    MessageKeys.ERROR_CONNECTING_CREATE_CUSTOMER_FEIGN,
                    "Error connecting to CreateCustomerFeign despite multiple retries, please try again later",
                    ex.getMessage());
        }
        return null;
    }

    /*
     * Generating timestamp
     */
    private String generateTimeStamp() {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        return utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.s'Z'"));
    }

}

