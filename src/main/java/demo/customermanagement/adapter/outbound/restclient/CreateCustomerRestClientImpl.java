package demo.customermanagement.adapter.outbound.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import demo.customermanagement.core.dto.restresponse.createcustomer.CreateCustomerResponse;
import demo.customermanagement.core.constant.MessageKeys;
import demo.customermanagement.core.dto.restrequest.createcustomer.CreateCustomerRequest;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import demo.customermanagement.core.port.outbound.CreateCustomerRestClient;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateCustomerRestClientImpl implements CreateCustomerRestClient {

  private static final String JSON_PROCESSING_ERROR =
      "Error while processing Json Response payload";
  private static final String ERROR_CREATING_CUSTOMER = "Error while creating customer detail";
  private static final String ERROR_READING_CREATE_CUSTOMER = "Error while reading create customer response";
  private final CreateCustomerFeignClient createCustomerFeignClient;
  private final MyExceptionBuilder exceptionBuilder;
  private final ObjectMapper objectMapper;

  @Override
  @CircuitBreaker(name = "CreateCustomerFeign")
  @Retry(name = "CreateCustomerFeign")
  @Bulkhead(name = "CreateCustomerFeign", type = Bulkhead.Type.SEMAPHORE)
  public CreateCustomerResponse createCustomer(
      final CreateCustomerRequest createCustomerRequest) {
    try {
      log.info("In CreateCustomerFeign feign client");
      return createCustomerFeignClient.createCustomer(createCustomerRequest);
    } catch (RetryableException ex) {
      log.error("In createProductOrder, retryable exception while connecting to CreateCustomerFeign", ex);
      throw ex;
    } catch (FeignException ex) {
      try {
        if (StringUtils.isNotEmpty(ex.contentUTF8())) {
          CreateCustomerResponse errorMessage =
              objectMapper.readValue(ex.contentUTF8(), CreateCustomerResponse.class);
          log.error("Error message from CreateCustomerFeign :: {}", objectMapper.writeValueAsString(errorMessage));
          if (ex.status() == 400) {
            exceptionBuilder.throwDataException(
                errorMessage.getStatus().getError().getErrorCode(),
                MessageKeys.ERROR_CREATING_CUSTOMER,
                errorMessage.getStatus().getError().getErrorDescription(),
                ex.getMessage());
          } else if (ex.status() == 404) {
            exceptionBuilder.throwResourceNotFoundException(
                errorMessage.getStatus().getError().getErrorCode(),
                MessageKeys.ERROR_CREATING_CUSTOMER,
                errorMessage.getStatus().getError().getErrorDescription(),
                ex.getMessage());
          }
        }
        exceptionBuilder.throwTechnicalException(
            "Adapter Error",
            MessageKeys.ERROR_CREATING_CUSTOMER,
            ERROR_CREATING_CUSTOMER,
            ex.getMessage());
      } catch (JsonProcessingException e) {
        log.error(ex.getLocalizedMessage());
        exceptionBuilder.throwTechnicalException(
            "Runtime Error",
            "Runtime Error",
            JSON_PROCESSING_ERROR,
            ex.getLocalizedMessage());
      }catch (IOException exception){
        log.error(ex.getLocalizedMessage());
        exceptionBuilder.throwTechnicalException(
                "Runtime Error",
                "Runtime Error",
                ERROR_READING_CREATE_CUSTOMER,
                ex.getLocalizedMessage());
      }
    }
    return null;
  }
}
