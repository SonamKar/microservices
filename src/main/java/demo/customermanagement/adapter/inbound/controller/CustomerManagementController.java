package demo.customermanagement.adapter.inbound.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import demo.customermanagement.core.dto.request.Customer_Create;
import demo.customermanagement.core.dto.response.customerdetails.Customer;
import demo.customermanagement.core.port.inbound.CreateCustomerPort;

@RestController
@RequestMapping(CustomerManagementController.CUSTOMER_MANAGEMENT_ROOT_URL)
@RequiredArgsConstructor
@Slf4j
public class CustomerManagementController {

  public static final String CUSTOMER_MANAGEMENT_ROOT_URL = "/customerManagement-ms/v1";

  private final CreateCustomerPort createCustomerPort;

  /**
   * The Customer Management MS API receives request to create customer
   * @param source Name of the requesting application/system
   * @param correlationId Id used to track the request in multiple systems
   * @param requestId Request/message Identifier from source
   * @param timeStamp Current timestamp when request was sent by source
   * @param createCustomer customer details for creation
   * @return Created Customer Object
   */

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Successful operation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Customer.class))
            })
      })
  @Operation(
      summary = "This operation creates customer deatils",
      security = {@SecurityRequirement(name = "basicAuth")})
  @PostMapping(
      value = "/customer",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<Customer> createCustomer(
      @NotEmpty @RequestHeader("source") String source,
      @RequestHeader(value = "correlationId", required = false) String correlationId,
      @RequestHeader(value = "requestId", required = false) String requestId,
      @NotEmpty @RequestHeader("timestamp") String timeStamp,
      @RequestBody Customer_Create createCustomer) {
    log.info("Received source :: {}", source);
    log.info("Received requestId :: {}", requestId);
    log.info("Received correlationId :: {}", correlationId);
    log.info("Received timestamp :: {}", timeStamp);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(createCustomerPort.createCustomerDetails(createCustomer, correlationId, source));
  }
}
