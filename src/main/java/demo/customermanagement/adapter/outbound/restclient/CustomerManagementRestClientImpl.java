package demo.customermanagement.adapter.outbound.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import demo.customermanagement.core.constant.MessageKeys;
import demo.customermanagement.core.dto.customerdetails.request.CreateCustomerRequest;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;
import demo.customermanagement.core.dto.customerdetails.restclient.ErrorDetails;
import demo.customermanagement.core.port.outbound.CustomerManagementRestClient;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerManagementRestClientImpl implements CustomerManagementRestClient {

  private static final String JSON_PROCESSING_ERROR =
          "Error while processing Json Response payload";
  private static final String CREATE_CUSTOMER_RESPONSE_PROCESSING_ERROR =
          "Error while processing Create Customer Response payload";
  private final CustomerManagementFeignClient customerManagementFeignClient;
  private static final String ERROR_CREATING_CUSTOMER = "Error while creating customer";
  private final MyExceptionBuilder exceptionBuilder;
  private final ObjectMapper objectMapper;

  @Override
  @CircuitBreaker(name = "CreateCustomerFeign")
  @Retry(name = "CreateCustomerFeign")
  @Bulkhead(name = "CreateCustomerFeign", type = Bulkhead.Type.SEMAPHORE)
  public CustomerResponse createCustomer(
          final String clientMsgId,
          final String correlationId,
          final String timestamp,
          final String systemId,
          final String serviceId,
          final CreateCustomerRequest createCustomerRequest) {
    try {
      log.info("CustomerManagementRestClientImpl class :: createCustomer() method In CreateCustomerFeign feign client success response");
      return customerManagementFeignClient
              .createCustomer(clientMsgId, correlationId, timestamp, systemId,
                      serviceId, createCustomerRequest);
    } catch (RetryableException ex) {
      log.error("In createCustomer, retryable exception while connecting to CreateCustomerFeign", ex);
      throw ex;
    } catch (FeignException ex) {
      try {
        if (StringUtils.isNotEmpty(ex.contentUTF8())) {
          log.error("contentUTF8 is not empty condition check");
          ErrorDetails errorMessage =
                  objectMapper.readValue(ex.contentUTF8(), ErrorDetails.class);
          log.error("Error message from CreateCustomerFeign :: {}", objectMapper.writeValueAsString(errorMessage));
          if (ex.status() == 400) {
            log.info("CustomerManagementRestClientImpl class :: createCustomer() method 400 error");
            exceptionBuilder.throwDataException(
                    errorMessage.getCode(),
                    MessageKeys.ERROR_CREATING_CUSTOMER,
                    errorMessage.getDescription(),
                    ex.getMessage());
          } else if (ex.status() == 404) {
            log.info("CustomerManagementRestClientImpl class :: createCustomer() method 404 error");
            exceptionBuilder.throwResourceNotFoundException(
                    errorMessage.getCode(),
                    MessageKeys.ERROR_CREATING_CUSTOMER,
                    errorMessage.getDescription(),
                    ex.getMessage());
          }
        }
        log.info("CustomerManagementRestClientImpl class :: createCustomer() method 500 error");
        exceptionBuilder.throwTechnicalException(
                "Adapter Error",
                MessageKeys.INPUT_HEADERS_MISSING,
                ERROR_CREATING_CUSTOMER,
                ex.getMessage());
      } catch (JsonProcessingException e) {
        log.info("CustomerManagementRestClientImpl class :: createCustomer() method JsonProcessingException");
        log.error(ex.getLocalizedMessage());
        exceptionBuilder.throwTechnicalException(
                "Runtime Error",
                "Runtime Error",
                JSON_PROCESSING_ERROR,
                ex.getLocalizedMessage());
      }
    }
    return null;
  }
}
