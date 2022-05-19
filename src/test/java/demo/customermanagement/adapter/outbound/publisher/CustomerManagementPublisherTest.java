package demo.customermanagement.adapter.outbound.publisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.customermanagement.config.CustomerManagementConfigTest;
import demo.customermanagement.core.exception.TechnicalException;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.Arrays;
import java.util.Collections;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
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
import demo.customermanagement.config.KafkaProducerConfig;
import demo.customermanagement.core.dto.customerdetails.response.Account;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;
import demo.customermanagement.core.dto.kafka.producedetails.Characteristic;
import demo.customermanagement.core.dto.kafka.producedetails.ContactMedium;
import demo.customermanagement.core.dto.kafka.producedetails.EngagedParty;
import demo.customermanagement.core.dto.kafka.producedetails.IndividualIdentification;
import demo.customermanagement.core.dto.kafka.producedetails.PartyCharacteristic;
import demo.customermanagement.core.dto.kafka.producedetails.ValidFor;
import demo.customermanagement.core.transformer.CustomerManagementMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {MyExceptionBuilder.class, ObjectMapper.class,
        KafkaProducerConfig.class})
@Import(CustomerManagementConfigTest.class)
@SpringBootTest(classes = {CustomerManagementPublisher.class})
public class CustomerManagementPublisherTest {
  @MockBean
  CustomerManagementMapper customerManagementMapper;
  @Autowired
  KafkaProducerConfig kafkaProducerConfig;
  @Autowired
  MyExceptionBuilder exceptionBuilder;
  @Autowired
  CustomerManagementPublisher customerManagementPublisher;
  private CustomerResponse customerResponse;

  @BeforeEach
  void init(){
    customerResponse= CustomerResponse.builder()
        .id("1140")
        .name("Moon Football Club")
        .account(Collections.singletonList(Account.builder()
            .id("acc-001")
            .build()))
        .build();

    demo.customermanagement.core.dto.kafka.producedetails.CustomerResponse customerResponseFromMapper=
        demo.customermanagement.core.dto.kafka.producedetails.CustomerResponse.newBuilder()
            .setId("123")
            .setName("Moon Football Club")
            .setEngagedParty(EngagedParty.newBuilder()
                .setAtReferredType("Organisation")
                .setBirthDate("01/0/1999")
                .setFullName("Moon Football Club")
                .setFamilyName("Club")
                .setGender("F")
                .setGivenName("Moon")
                .setMiddleName("")
                .setNationality("Indian")
                .setContactMedium(Arrays.asList(ContactMedium.newBuilder()
                    .setMediumType("Informal")
                    .setCharacteristic(Characteristic.newBuilder()
                        .setEmailAddress("moonfootballclub@gmail.com")
                        .setPhoneNumber("8249612544")
                        .build())
                    .build()))
                .setPartyCharacteristic(Arrays.asList(PartyCharacteristic.newBuilder()
                    .setName("abc")
                    .setValue("rupees")
                    .setValueType("anything")
                    .build()))
                .setIndividualIdentification(Arrays.asList(IndividualIdentification.newBuilder()
                    .setIdentificationId("245-9")
                    .setIssuingAuthority("govt")
                    .setIdentificationType("passport")
                    .setIssuingDate("25/01/2022")
                    .setPlaceOfIssue("mumbai")
                    .setValidFor(ValidFor.newBuilder()
                        .setStartDateTime("25/01/2022")
                        .setEndDateTime("25/01/2029")
                        .build())
                    .build()))
                .build())
            .setAccount(Arrays.asList(
                demo.customermanagement.core.dto.kafka.producedetails.Account.newBuilder()
                    .setAtReferredType("Normal")
                    .setId("acc-2554")
                    .build()))
            .build();

    when(customerManagementMapper.mapToKafkaProduceDetails(customerResponse)).thenReturn(customerResponseFromMapper);

  }
  @Test
  void publishToKafka_Success(){

    MockProducer<String,Object> mockProducer= new MockProducer<>(true,new StringSerializer(),
        new KafkaAvroSerializer());
    customerManagementPublisher.setProducer(mockProducer);
    customerManagementPublisher.publishToKafka(customerResponse);

    Assertions.assertEquals(1, mockProducer.history().size());
  }

  @Test
  void publishToKafka_Error(){
    customerManagementPublisher.initializeProducer();
    TechnicalException ex=assertThrows(TechnicalException.class,
        ()->customerManagementPublisher.publishToKafka(customerResponse));
    assertEquals("Error while publishing to kafka topic",ex.getErrorMessageCode());
    assertEquals("Runtime Error",ex.getCode());
  }
}
