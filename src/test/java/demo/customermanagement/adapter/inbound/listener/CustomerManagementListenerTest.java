package demo.customermanagement.adapter.inbound.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.customermanagement.config.CustomerManagementConfigTest;
import demo.customermanagement.core.exception.TechnicalException;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.DisconnectException;
import org.apache.kafka.common.record.TimestampType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import demo.customermanagement.adapter.outbound.publisher.CustomerManagementPublisher;
import demo.customermanagement.core.dto.customerdetails.response.Account;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;
import demo.customermanagement.core.dto.kafka.consumedetails.*;
import demo.customermanagement.core.port.inbound.CustomerManagementPort;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {MyExceptionBuilder.class, ObjectMapper.class})
@Import(CustomerManagementConfigTest.class)
@SpringBootTest(classes = {CustomerManagementListener.class})
public class CustomerManagementListenerTest {
  @MockBean
  CustomerManagementPort customerManagementPort;
  @MockBean
  CustomerManagementPublisher customerManagementPublisher;
  @Autowired
  MyExceptionBuilder exceptionBuilder;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  CustomerManagementListener customerManagementListener;
  private customerCreateEvent customerCreateEventRequest;
  @Mock
  private Acknowledgment acknowledgment;

  @BeforeEach
  void init() {
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
                        .setBirthDate("")
                        .setFullName("")
                        .setFamilyName("")
                        .setGender("")
                        .setGivenName("")
                        .setMiddleName("")
                        .setNationality("")
                        .setContactMedium(contactMedium_record.newBuilder()
                            .setMediumType("")
                            .setCharacteristic(characteristic_record.newBuilder()
                                .setEmailAddress("")
                                .setPhoneNumber("")
                                .build())
                            .build())
                        .setIndividualIdentification(individualIdentification_record.newBuilder()
                            .setIdentificationId("")
                            .setIdentificationType("")
                            .setIssuingAuthority("")
                            .setPlaceOfIssue("")
                            .setIssuingDate("")
                            .setValidFor(validFor_record.newBuilder()
                                .setStartDateTime("")
                                .setEndDateTime("")
                                .build())
                            .build())
                        .setPartyCharacteristic(partyCharacteristic_record.newBuilder()
                            .setName("")
                            .setValue("")
                            .setValueType("")
                            .build())
                        .build())
                    .setValidFor(validFor.newBuilder()
                        .setStartDateTime("2018-06-12T00:00Z")
                        .setEndDateTime("2019-01-11T00:00Z")
                        .build())
                    .build())
                .build())
            .build();
  }

  @Test
  void consumeKafkaPayload_Success() {

    ConsumerRecord<Integer, customerCreateEvent> record = new ConsumerRecord<>
        ("test", 1, 0, 0L, TimestampType.CREATE_TIME, 0L,
            0, 0, 1, customerCreateEventRequest);
    CustomerResponse customerResponse= CustomerResponse.builder()
        .id("1140")
        .name("Moon Football Club")
        .account(Collections.singletonList(Account.builder()
            .id("acc-001")
            .build()))
        .build();
    when(customerManagementPort.createCustomer(customerCreateEventRequest)).thenReturn(customerResponse);
    customerManagementListener.consumeKafkaPayload(record, acknowledgment);
    verify(customerManagementPort,times(1)).createCustomer(customerCreateEventRequest);
    verify(customerManagementPublisher,times(1)).publishToKafka(customerResponse);
  }
  @Test
  void consumeKafkaPayload_DisconnectException(){
    ConsumerRecord<Integer, customerCreateEvent> record = new ConsumerRecord<>
        ("test", 1, 0, 0L, TimestampType.CREATE_TIME, 0L,
            0, 0, 1, customerCreateEventRequest);
    when(customerManagementPort.createCustomer(customerCreateEventRequest)).thenThrow(
        new DisconnectException());

    TechnicalException ex=assertThrows(
        TechnicalException.class,
        () -> customerManagementListener.consumeKafkaPayload(record,acknowledgment));
    assertEquals("Runtime Error",ex.getCode());
    assertEquals("Error while connecting to kafka topic",ex.getErrorMessageCode());
  }
}
