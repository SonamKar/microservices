package demo.customermanagement.adapter.inbound.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;

import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.DisconnectException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import demo.customermanagement.adapter.outbound.publisher.CustomerManagementPublisher;
import demo.customermanagement.core.dto.kafka.consumedetails.customerCreateEvent;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;
import demo.customermanagement.core.logging.JacksonIgnoreAvroPropertiesMixIn;
import demo.customermanagement.core.port.inbound.CustomerManagementPort;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerManagementListener {

  private static final String JSON_PROCESSING_ERROR =
          "Error while processing Json Response payload";
  private static final String KAFKA_CONNECTION_ERROR = "Error while connecting to kafka topic";

  private final CustomerManagementPort customerManagementPort;
  private final CustomerManagementPublisher customerManagementPublisher;
  private final MyExceptionBuilder exceptionBuilder;
  final ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(
          topics = "${spring.kafka.consumer.topic}",
          containerFactory = "kafkaListenerContainerFactory")
  public void consumeKafkaPayload(
          @Payload ConsumerRecord consumerRecord, Acknowledgment acknowledgment) {
    try {
      log.info("CustomerManagementListener class :: consumePayloadAndSendRequestKafka method");
      log.info("Payload received from Kafka :: {}", consumerRecord);

      customerCreateEvent customerCreateEvent = (customerCreateEvent) consumerRecord.value();

      log.info("customerCreateEvent Object {} ", objectMapper
              .addMixIn(org.apache.avro.specific.SpecificRecord.class,
                      JacksonIgnoreAvroPropertiesMixIn.class).writeValueAsString(customerCreateEvent));

      if(!Objects.isNull(customerCreateEvent)){
        CustomerResponse customerResponse =
                customerManagementPort.createCustomer(customerCreateEvent);
        log.info("createCustomerResponse Object {} ", objectMapper.writeValueAsString(customerResponse));
        if(!Objects.isNull(customerResponse)){
          log.info("Response from CreateCustomerFeign is not null :: {}",customerResponse);
          customerManagementPublisher.initializeProducer();
          customerManagementPublisher.publishToKafka(customerResponse);
        }
        acknowledgment.acknowledge();
      }

    } catch (DisconnectException e) {
      log.info("Disconnect Exception occurred");
      exceptionBuilder.throwTechnicalException(
              "Runtime Error",
              "Runtime Error",
              KAFKA_CONNECTION_ERROR,
              e.getLocalizedMessage()
      );
    } catch (JsonProcessingException ex) {
      log.info("JsonProcessingException occurred while parsing kafka payload");
      exceptionBuilder.throwTechnicalException(
              "Runtime Error",
              "Runtime Error",
              JSON_PROCESSING_ERROR,
              ex.getLocalizedMessage());
    }
  }
}
