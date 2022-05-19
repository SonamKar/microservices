package demo.customermanagement.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.customermanagement.core.constant.Constants;
import demo.customermanagement.core.dto.request.Customer_Create;
import demo.customermanagement.core.port.inbound.CreateCustomerPort;
import demo.customermanagement.core.port.outbound.CreateCustomerRestClient;
import feign.RetryableException;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import demo.customermanagement.core.dto.response.customerdetails.Customer;
import demo.customermanagement.core.dto.restrequest.createcustomer.CreateCustomerRequest;
import demo.customermanagement.core.dto.restrequest.createcustomer.Header;
import demo.customermanagement.core.dto.restresponse.createcustomer.CreateCustomerResponse;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import demo.customermanagement.core.constant.MessageKeys;


@Service
@RequiredArgsConstructor
@Slf4j
public class CreateCustomerService implements CreateCustomerPort {

    private static final String JSON_PROCESSING_ERROR =
            "Error while processing Json Response payload";
    private static final String NO_RESPONSE_PAYLOAD_FROM_CREATE_CUSTOMER_FEIGN =
            "Response payload not received from CreateCustomerFeign";

    @Value("${COM.consumer.bootstrap-servers}")
    private String consumerBootstrapServers;

    @Value("${COM.consumer.groupId}")
    private String consumerGroupId;

    @Value("${COM.consumer.topic}")
    private String topic;

    private final CreateCustomerRestClient createCustomerRestClient;
    private final MyExceptionBuilder exceptionBuilder;
    private final ObjectMapper objectMapper;

    @Override
    public Customer createCustomerDetails(
            final Customer_Create createCustomer,
            final String correlationId,
            final String source) {
        try {
            log.info(
                    "Create Customer request received :: {} ",
                    objectMapper.writeValueAsString(createCustomer));

                CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                        .header(constructHeader(source, correlationId))
                        .body(createCustomer)
                        .build();

            log.info(
                    "Request sent to CreateCustomerFeign :: {} ",
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createCustomerRequest));
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createCustomerRequest);

            System.out.println(json);

            /*
             * invoking CreateCustomerFeign
             */
            CreateCustomerResponse createCustomerResponse =
                    callCreateCustomer(createCustomerRequest);
            log.info(
                    "Response received from CreateCustomerFeign :: {} ",
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createCustomerResponse));
            String res = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createCustomerResponse);
            System.out.println(res);

            if(ObjectUtils.isEmpty(createCustomerResponse)){
                exceptionBuilder.throwResourceNotFoundException(
                        "Adapter Error",
                        MessageKeys.NO_RESPONSE_PAYLOAD_FROM_CREATE_CUSTOMER_FEIGN,
                        NO_RESPONSE_PAYLOAD_FROM_CREATE_CUSTOMER_FEIGN,
                        NO_RESPONSE_PAYLOAD_FROM_CREATE_CUSTOMER_FEIGN);
            }
        } catch (CallNotPermittedException | BulkheadFullException ex) {
            exceptionBuilder.throwTechnicalException(
                    "Adapter Error",
                    MessageKeys.ERROR_CONNECTING_CREATE_CUSTOMER_FEIGN,
                    "Error connecting to downstream MS, please try after sometime",
                    ex.getMessage());
        } catch (RetryableException ex) {
            exceptionBuilder.throwTechnicalException(
                    "Adapter Error",
                    MessageKeys.ERROR_CONNECTING_CREATE_CUSTOMER_FEIGN,
                    "Error connecting to downstream MS despite multiple retries, please try again later",
                    ex.getMessage());
        } catch (JsonProcessingException ex) {
            exceptionBuilder.throwTechnicalException(
                    "Runtime Error",
                    "Runtime Error",
                    JSON_PROCESSING_ERROR,
                    ex.getLocalizedMessage());
        }
        return null;
    }


    /*
     * Constructing header for CreateCustomerFeign request
     */
    private Header constructHeader(final String source, final String correlationId) {
        return Header.builder()
                .systemId(source)
                .serverInfo(getIPAddress())
                .messageId(correlationId)
                .timestamp(generateTimeStamp())
                .domainId(Constants.DOMAIN_ID)
                .serviceId(Constants.SERVICE_ID)
                .operationType(Constants.OPERATION_TYPE)
                .build();
    }

    /*
     * Generate IP Address
     */
    private String getIPAddress() {
        try {
            final InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("Exception occurred while retrieving ip address :: {}", e.getMessage());
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


    /*
     * invoking CreateCustomerFeign
     */
    private CreateCustomerResponse callCreateCustomer(
            final CreateCustomerRequest createCustomerRequest) {
        try {
            return createCustomerRestClient.createCustomer(createCustomerRequest);
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
}
