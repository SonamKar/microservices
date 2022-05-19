package demo.customermanagement.adapter.inbound.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import demo.customermanagement.core.dto.kafkaresponse.event_record;
import demo.customermanagement.core.dto.request.Customer_Create;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import demo.customermanagement.core.port.inbound.CreateCustomerPort;
import demo.customermanagement.core.transformer.CreateCustomerMapper;
import demo.customermanagement.core.logging.JacksonIgnoreAvroPropertiesMixIn;

import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventListener {

    private static final String JSON_PROCESSING_ERROR =
            "Error while processing Json Response payload";

    @Autowired
    private ConcurrentMap<Object, Object> mapCache;

    private final CreateCustomerPort createCustomerService;
    private final CreateCustomerMapper createCustomerMapper;
    private final MyExceptionBuilder exceptionBuilder;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${COM.consumer.topic}",
            groupId = "${COM.consumer.groupId}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumePayload(final ConsumerRecord consumerRecord, final Acknowledgment acknowledgment) {
        try {
//            event_record event_record = objectMapper.readValue(consumerRecord.value().toString(), event_record.class);
            event_record event_record = (event_record) consumerRecord.value();
            log.info("kafka record received :: {} ", objectMapper.addMixIn(
                    org.apache.avro.specific.SpecificRecord.class,
                    JacksonIgnoreAvroPropertiesMixIn.class).writeValueAsString(event_record));
            System.out.println("payload  ::" + consumerRecord.value());
            Customer_Create createCustomerRequest=createCustomerMapper.constructCustomerRequest(event_record);
            createCustomerService.createCustomerDetails(createCustomerRequest,"","");
            acknowledgment.acknowledge();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            exceptionBuilder.throwTechnicalException(
                    "Runtime Error",
                    "Runtime Error",
                    JSON_PROCESSING_ERROR,
                    ex.getLocalizedMessage());
        }
    }
}

