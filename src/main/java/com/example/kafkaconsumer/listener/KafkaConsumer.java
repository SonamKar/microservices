package com.example.kafkaconsumer.listener;

import com.example.kafkaconsumer.domain.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class KafkaConsumer {
  
	@KafkaListener(topics = "testProducer")
    public void consume(@RequestBody User user) {
    System.out.println("Consumed message: " + user);
    }


}
