package demo.customermanagement.adapter.outbound.publisher;

import demo.customermanagement.config.KafkaProducerConfig;
import demo.customermanagement.core.dto.customerdetails.response.CustomerResponse;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import demo.customermanagement.core.transformer.CustomerManagementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerManagementPublisher {

  private static final String KAFKA_PUBLISH_ERROR = "Error while publishing to kafka topic";
  @Autowired
  KafkaProducerConfig kafkaProducerConfig;
  @Autowired
  MyExceptionBuilder exceptionBuilder;
  @Autowired
  CustomerManagementMapper customerManagementMapper;
  @Value("${spring.kafka.producer.topic}")
  private String topic;
  private Producer<String, Object> producer;

  public void setProducer(
      Producer<String, Object> producer) {
    this.producer = producer;
  }

  public void initializeProducer(){
   this.setProducer(kafkaProducerConfig.createProducer());
  }

  public void publishToKafka(CustomerResponse customerResponse) {
    log.info("Publishing to kafka topic");
    producer.send(new ProducerRecord<>(topic,
                    customerManagementMapper.mapToKafkaProduceDetails(customerResponse)),
        (recordMetadata, e) -> {
          if (e == null) {
            log.info("Successfully record published to Kafka topic");
            log.info("topic :: {} ,partition :: {},offset :: {}, timestamp :: {}"
                , recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(),
                recordMetadata.timestamp());
          } else {
            exceptionBuilder.throwTechnicalException(
                "Runtime Error",
                "Runtime Error",
                KAFKA_PUBLISH_ERROR,
                e.getLocalizedMessage()
            );
          }
        });
    producer.flush();
    producer.close();
  }
}
