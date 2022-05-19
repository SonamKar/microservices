package demo.customermanagement.adapter.outbound.restclient;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.customermanagement.config.CustomerManagementConfigTest;
import demo.customermanagement.core.exception.DataException;
import demo.customermanagement.core.exception.ResourceNotFoundException;
import demo.customermanagement.core.exception.TechnicalException;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import feign.FeignException;
import feign.FeignException.FeignClientException;
import feign.Request;
import feign.RetryableException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import demo.customermanagement.core.dto.customerdetails.ContactMedium;
import demo.customermanagement.core.dto.customerdetails.EngagedParty;
import demo.customermanagement.core.dto.customerdetails.IndividualIdentification;
import demo.customermanagement.core.dto.customerdetails.MediumCharacteristic;
import demo.customermanagement.core.dto.customerdetails.PartyCharacteristic;
import demo.customermanagement.core.dto.customerdetails.ValidFor;
import demo.customermanagement.core.dto.customerdetails.request.CreateCustomerRequest;
import demo.customermanagement.core.dto.customerdetails.response.Account;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MyExceptionBuilder.class})
@Import(CustomerManagementConfigTest.class)
@SpringBootTest(classes = {CustomerManagementRestClientImpl.class, MyExceptionBuilder.class,
        ObjectMapper.class})
public class CustomerManagementRestClientImplTest {

  @Autowired
  private CustomerManagementRestClientImpl customerManagementRestClientImpl;
  @MockBean
  private CustomerManagementFeignClient customerManagementFeignClient;
  private CreateCustomerRequest createCustomerRequest;

  @BeforeEach
  void init() {

    createCustomerRequest = CreateCustomerRequest.builder()
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

  }

  @Test
  void createCustomerTest_Success() {
    CustomerResponse customerResponse = CustomerResponse.builder()
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

    when(customerManagementFeignClient
            .createCustomer(anyString(), anyString(), anyString(), anyString(), anyString(),
                    any())).thenReturn(customerResponse);

    CustomerResponse customerResponseFromRestClient =
            customerManagementRestClientImpl.createCustomer("123e4567-e89b-42d3-a456-556642440000",
                    "123e4567-e89b-42d3-a456-556642440000", "2019-01-11T00:00Z", "COM",
                    "createCustomer", createCustomerRequest);

    Assertions.assertEquals("Moon Football Club", customerResponseFromRestClient.getName());
    Assertions.assertEquals("2554", customerResponseFromRestClient.getAccount()
            .get(0).getId());
    Assertions.assertEquals("01/01/1999", customerResponseFromRestClient
            .getEngagedParty().getBirthDate());
    Assertions.assertEquals("moonfootballclub@gmail.com", customerResponseFromRestClient
            .getEngagedParty().getContactMedium().get(0).getCharacteristic().getEmailAddress());
    Assertions.assertEquals("anything", customerResponseFromRestClient
            .getEngagedParty().getPartyCharacteristic().get(0).getValueType());
    Assertions.assertEquals("2019-01-11T00:00Z", customerResponseFromRestClient
            .getEngagedParty().getIndividualIdentification().get(0).getValidFor().getEndDateTime());
  }

  @Test
  void createCustomerTest_FeignClientException_404() {
    final String errorMessage =
            "{\"code\":\"CRM.01\",\"description\":\"notFound\",\"reference\":\"reference1\"}";
    final byte[] data = errorMessage.getBytes(StandardCharsets.UTF_8);

    final Map<String, Collection<String>> map = new HashMap<>();
    map.put("Content-Type", Collections.singleton("application/json"));
    map.put("Accept", Collections.singleton("application/json"));

    final FeignClientException feignClientException =
            new FeignException.FeignClientException(
                    404,
                    "not found",
                    Request.create(
                            Request.HttpMethod.GET, "http://localhost", map, data,
                            StandardCharsets.UTF_8, null), data,map);
    when(customerManagementFeignClient
            .createCustomer(anyString(), anyString(), anyString(), anyString(), anyString(),
                    any()))
            .thenThrow(feignClientException);
    Assertions.assertThrows(ResourceNotFoundException.class,
            () -> customerManagementRestClientImpl
                    .createCustomer("123e4567-e89b-42d3-a456-556642440000",
                            "123e4567-e89b-42d3-a456-556642440000", "2019-01-11T00:00Z", "COM",
                            "createCustomer",
                            createCustomerRequest));
  }

  @Test
  void createCustomerTest_FeignClientException_400() {
    final String errorMessage =
            "{\"code\":\"CRM.01\",\"description\":\"notFound\",\"reference\":\"reference1\"}";
    final byte[] data = errorMessage.getBytes(StandardCharsets.UTF_8);

    final Map<String, Collection<String>> map = new HashMap<>();
    map.put("Content-Type", Collections.singleton("application/json"));
    map.put("Accept", Collections.singleton("application/json"));

    final FeignClientException feignClientException =
            new FeignException.FeignClientException(
                    400,
                    "bad request",
                    Request.create(
                            Request.HttpMethod.GET, "http://localhost", map, data,
                            StandardCharsets.UTF_8, null), data,map);
    when(customerManagementFeignClient
            .createCustomer(anyString(), anyString(), anyString(), anyString(), anyString(),
                    any()))
            .thenThrow(feignClientException);
    Assertions.assertThrows(DataException.class,
            () -> customerManagementRestClientImpl
                    .createCustomer("123e4567-e89b-42d3-a456-556642440000",
                            "123e4567-e89b-42d3-a456-556642440000", "2019-01-11T00:00Z", "COM",
                            "createCustomer",
                            createCustomerRequest));
  }

  @Test
  void createCustomerTest_FeignClientException_500() {
    final FeignClientException feignClientException =
            new FeignException.FeignClientException(
                    500,
                    "internal server error",
                    Request.create(
                            Request.HttpMethod.GET, "http://localhost", new HashMap<>(), null,
                            null, null), null,null);
    when(customerManagementFeignClient
            .createCustomer(anyString(), anyString(), anyString(), anyString(), anyString(),
                    any()))
            .thenThrow(feignClientException);
    Assertions.assertThrows(TechnicalException.class,
            () -> customerManagementRestClientImpl
                    .createCustomer("123e4567-e89b-42d3-a456-556642440000",
                            "123e4567-e89b-42d3-a456-556642440000", "2019-01-11T00:00Z", "COM",
                            "createCustomer",
                            createCustomerRequest));
  }

  @Test
  void createCustomerTest_RetryableExceptionTest() {
    when(customerManagementFeignClient
            .createCustomer(anyString(), anyString(), anyString(), anyString(), anyString(),
                    any()))
            .thenThrow(RetryableException.class);
    assertThrows(
            RetryableException.class,
            () -> customerManagementRestClientImpl
                    .createCustomer("123e4567-e89b-42d3-a456-556642440000",
                            "123e4567-e89b-42d3-a456-556642440000", "2019-01-11T00:00Z", "COM",
                            "createCustomer",
                            createCustomerRequest));
  }

  @Test
  void createCustomerTest_JsonProcessingExceptionTest() {
    final String errorMessage =
            "{\"code1\":\"CRM.01\",\"description\":\"notFound\",\"reference\":\"reference1\"}";
    final byte[] data = errorMessage.getBytes(StandardCharsets.UTF_8);

    final FeignClientException feignClientException =
            new FeignException.FeignClientException(
                    500,
                    "internal server error",
                    Request.create(
                            Request.HttpMethod.GET, "http://localhost", new HashMap<>(), null,
                            null, null), data,null);
    when(customerManagementFeignClient
            .createCustomer(anyString(), anyString(), anyString(), anyString(), anyString(),
                    any()))
            .thenThrow(feignClientException);
    TechnicalException ex=Assertions.assertThrows(TechnicalException.class,
            () -> customerManagementRestClientImpl
                    .createCustomer("123e4567-e89b-42d3-a456-556642440000",
                            "123e4567-e89b-42d3-a456-556642440000", "2019-01-11T00:00Z", "COM",
                            "createCustomer",
                            createCustomerRequest));
    Assertions.assertEquals("Error while processing Json Response payload",ex.getErrorMessageCode());
  }

}
