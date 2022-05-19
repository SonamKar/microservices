package demo.customermanagement.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.customermanagement.config.CustomerManagementConfigTest;
import demo.customermanagement.core.exception.ResourceNotFoundException;
import demo.customermanagement.core.exception.TechnicalException;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import feign.RetryableException;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import demo.customermanagement.core.dto.customerdetails.*;
import demo.customermanagement.core.dto.customerdetails.request.CreateCustomerRequest;
import demo.customermanagement.core.dto.customerdetails.response.Account;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;
import demo.customermanagement.core.dto.kafka.consumedetails.*;
import demo.customermanagement.core.port.outbound.CustomerManagementRestClient;
import demo.customermanagement.core.transformer.CustomerManagementMapper;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {
                MyExceptionBuilder.class,
                ObjectMapper.class,
        })
@Import(CustomerManagementConfigTest.class)
@SpringBootTest(classes = {CustomerManagementService.class})
public class CustomerManagementServiceTest {
  @MockBean
  CustomerManagementMapper customerManagementMapper;
  @MockBean
  CustomerManagementRestClient customerManagementRestClientImpl;
  @Autowired
  MyExceptionBuilder exceptionBuilder;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  CustomerManagementService customerManagementService;
  private customerCreateEvent
          customerCreateEventRequest;
  private CreateCustomerRequest createCustomerRequest;
  private CustomerResponse customerResponse;
  @BeforeEach
  void initialize(){
    customerCreateEventRequest =
            customerCreateEvent.newBuilder()
                    .setEventId("00001")
                    .setEventTime("2015-11-16T16:42:25-04:00")
                    .setEventType("CustomerDeleteEvent")
                    .setEvent(event_record.newBuilder()
                            .setCustomer(customer_record.newBuilder()
                                    .setAtType("Customer")
                                    .setHref("https://host:port/tmf-api/customerManagement/v4/customer/1140")
                                    .setId("1140")
                                    .setName("Moon Football Club")
                                    .setStatus("Approved")
                                    .setStatusReason("Account details checked")
                                    .setEngagedParty(engagedParty_record.newBuilder()
                                            .setAtReferredType("Organization")
                                            .setBirthDate("01/01/1999")
                                            .setFullName("Moon Football Club")
                                            .setFamilyName("Club")
                                            .setGender("F")
                                            .setGivenName("Moon")
                                            .setMiddleName("Football")
                                            .setNationality("Indian")
                                            .setContactMedium(contactMedium_record.newBuilder()
                                                    .setMediumType("Informal")
                                                    .setCharacteristic(characteristic_record.newBuilder()
                                                            .setEmailAddress("moonfootballclub@gmail.com")
                                                            .setPhoneNumber("8249565446")
                                                            .build())
                                                    .build())
                                            .setIndividualIdentification(individualIdentification_record.newBuilder()
                                                    .setIdentificationId("245-9")
                                                    .setIdentificationType("passport")
                                                    .setIssuingAuthority("govt")
                                                    .setPlaceOfIssue("mumbai")
                                                    .setIssuingDate("25/01/2022")
                                                    .setValidFor(validFor_record.newBuilder()
                                                            .setStartDateTime("2018-06-12T00:00Z")
                                                            .setEndDateTime("2019-01-11T00:00Z")
                                                            .build())
                                                    .build())
                                            .setPartyCharacteristic(partyCharacteristic_record.newBuilder()
                                                    .setName("abc")
                                                    .setValue("rupees")
                                                    .setValueType("anything")
                                                    .build())
                                            .build())
                                    .setValidFor(validFor.newBuilder()
                                            .setStartDateTime("2018-06-12T00:00Z")
                                            .setEndDateTime("2019-01-11T00:00Z")
                                            .build())
                                    .build())
                            .build())
                    .build();

    createCustomerRequest= CreateCustomerRequest.builder()
            .name("Moon Football Club")
            .engagedParty(EngagedParty.builder()
                    .birthDate("01/01/1999")
                    .familyName("Club")
                    .fullName("Moon Football Club")
                    .gender("F")
                    .givenName("Moon")
                    .middleName("football")
                    .referredType("Organization")
                    .contactMedium(Collections.singletonList(ContactMedium.builder()
                            .mediumType("Informal")
                            .characteristic(MediumCharacteristic.builder()
                                    .emailAddress("moonfootbalclub@gmail.com")
                                    .phoneNumber("8249565446")
                                    .build())
                            .build()))
                    .individualIdentification(Collections.singletonList(IndividualIdentification.builder()
                            .issuingDate("25/01/2022")
                            .placeOfIssue("mumbai")
                            .issuingAuthority("govt")
                            .identificationType("passport")
                            .identificationId("245-9")
                            .validFor(ValidFor.builder()
                                    .startDateTime("2018-06-12T00:00Z")
                                    .endDateTime("2019-01-11T00:00Z")
                                    .build())
                            .build()))
                    .nationality("Indian")
                    .partyCharacteristic(Collections.singletonList(PartyCharacteristic.builder()
                            .valueType("anything")
                            .value("rupees")
                            .name("abc")
                            .build()))
                    .build())
            .build();

    customerResponse= CustomerResponse.builder()
            .id("1140")
            .name("Moon Football Club")
            .account(Collections.singletonList(Account.builder()
                    .id("2554")
                    .referredType("account")
                    .build()))
            .engagedParty(EngagedParty.builder()
                    .birthDate("01/01/1999")
                    .familyName("Club")
                    .fullName("Moon Football Club")
                    .gender("F")
                    .givenName("Moon")
                    .middleName("Football")
                    .referredType("Organization")
                    .contactMedium(Collections.singletonList(ContactMedium.builder()
                            .mediumType("Informal")
                            .characteristic(MediumCharacteristic.builder()
                                    .emailAddress("moonfootballclub@gmail.com")
                                    .phoneNumber("8249565446")
                                    .build())
                            .build()))
                    .individualIdentification(Collections.singletonList(IndividualIdentification.builder()
                            .issuingDate("25/01/2022")
                            .placeOfIssue("mumbai")
                            .issuingAuthority("govt")
                            .identificationType("passport")
                            .identificationId("245-9")
                            .validFor(ValidFor.builder()
                                    .startDateTime("2018-06-12T00:00Z")
                                    .endDateTime("2019-01-11T00:00Z")
                                    .build())
                            .build()))
                    .nationality("Indian")
                    .partyCharacteristic(Collections.singletonList(PartyCharacteristic.builder()
                            .valueType("anything")
                            .value("rupees")
                            .name("abc")
                            .build()))
                    .build())
            .build();
  }
  @Test
  void createCustomer_Success(){

    when(customerManagementMapper.constructCreateCustomerRequest(customerCreateEventRequest))
            .thenReturn(createCustomerRequest);

    when(customerManagementRestClientImpl.createCustomer(anyString(),anyString(),anyString(),anyString(),anyString(),
            Mockito.any())).thenReturn(customerResponse);

    CustomerResponse receivedCustomerResponse=customerManagementService.createCustomer(customerCreateEventRequest);

    Assertions.assertEquals("1140",receivedCustomerResponse.getId());
    Assertions.assertEquals("Moon Football Club",receivedCustomerResponse.getName());
    Assertions.assertEquals("01/01/1999",receivedCustomerResponse.getEngagedParty().getBirthDate());
    Assertions.assertEquals("Club",receivedCustomerResponse.getEngagedParty().getFamilyName());
    Assertions.assertEquals("Moon Football Club",receivedCustomerResponse.getEngagedParty().getFullName());
    Assertions.assertEquals("F",receivedCustomerResponse.getEngagedParty().getGender());
    Assertions.assertEquals("Informal",receivedCustomerResponse.getEngagedParty()
            .getContactMedium().get(0).getMediumType());
    Assertions.assertEquals("moonfootballclub@gmail.com",receivedCustomerResponse.getEngagedParty()
            .getContactMedium().get(0).getCharacteristic().getEmailAddress());
    Assertions.assertEquals("govt",receivedCustomerResponse.getEngagedParty()
            .getIndividualIdentification().get(0).getIssuingAuthority());
    Assertions.assertEquals("mumbai",receivedCustomerResponse.getEngagedParty()
            .getIndividualIdentification().get(0).getPlaceOfIssue());
    Assertions.assertEquals("2018-06-12T00:00Z",receivedCustomerResponse.getEngagedParty()
            .getIndividualIdentification().get(0).getValidFor().getStartDateTime());
    Assertions.assertEquals("anything",receivedCustomerResponse.getEngagedParty()
            .getPartyCharacteristic().get(0).getValueType());
    Assertions.assertEquals("2554",receivedCustomerResponse.getAccount().get(0).getId());
  }

  @Test
  void createCustomer_ResourceNotFoundException(){
    customerResponse=null;
    when(customerManagementMapper.constructCreateCustomerRequest(customerCreateEventRequest))
            .thenReturn(createCustomerRequest);

    when(customerManagementRestClientImpl.createCustomer(anyString(),anyString(),anyString(),anyString(),anyString(),
            any())).thenReturn(customerResponse);

    ResourceNotFoundException exception=Assertions.assertThrows(ResourceNotFoundException.class,
            ()->customerManagementService.createCustomer(customerCreateEventRequest));
    Assertions.assertEquals("Adapter Error",exception.getCode());
    Assertions.assertEquals("Response payload not received from CreateCustomerFeign",exception.getErrorMessageCode());

  }
  @Test
  void createCustomer_BulkheadFullException(){
    when(customerManagementRestClientImpl.createCustomer(anyString(),anyString(),anyString(),anyString(),anyString(),
            any()))
            .thenThrow(BulkheadFullException.class);

    when(customerManagementMapper.constructCreateCustomerRequest(customerCreateEventRequest))
            .thenReturn(createCustomerRequest);
    TechnicalException ex=Assertions.assertThrows(TechnicalException.class,
            ()->customerManagementService.createCustomer(customerCreateEventRequest));
    Assertions.assertEquals("Adapter Error",ex.getCode());
    Assertions.assertEquals("Error connecting to CreateCustomerFeign, please try after sometime",ex.getErrorMessageCode());
  }

  @Test
  void createCustomer_CallNotPermittedException(){
    when(customerManagementRestClientImpl.createCustomer(anyString(),anyString(),anyString(),anyString(),anyString(),
            any()))
            .thenThrow(CallNotPermittedException.class);

    when(customerManagementMapper.constructCreateCustomerRequest(customerCreateEventRequest))
            .thenReturn(createCustomerRequest);
    TechnicalException ex=Assertions.assertThrows(TechnicalException.class,
            ()->customerManagementService.createCustomer(customerCreateEventRequest));
    Assertions.assertEquals("Adapter Error",ex.getCode());
    Assertions.assertEquals("Error connecting to CreateCustomerFeign, please try after sometime",ex.getErrorMessageCode());
  }

  @Test
  void createCustomer_RetryableException(){
    when(customerManagementRestClientImpl.createCustomer(anyString(),anyString(),anyString(),anyString(),anyString(),
            any()))
            .thenThrow(RetryableException.class);

    when(customerManagementMapper.constructCreateCustomerRequest(customerCreateEventRequest))
            .thenReturn(createCustomerRequest);

    TechnicalException ex=Assertions.assertThrows(TechnicalException.class,
            ()->customerManagementService.createCustomer(customerCreateEventRequest));
    Assertions.assertEquals("Adapter Error",ex.getCode());
    Assertions.assertEquals("Error connecting to CreateCustomerFeign despite multiple retries, please try again later",ex.getErrorMessageCode());
  }
}
