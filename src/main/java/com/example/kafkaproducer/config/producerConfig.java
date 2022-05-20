package com.example.kafkaproducer.config;

import com.example.kafkaproducer.domain.User;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class producerConfig {


    @Value("${producer.bootstrap-servers}")
    private String bootstrap_servers;

    @Value("${producer.key-serializer}")
    private String key_serializer;

    @Value("${producer.value-serializer}")
    private String value_serializer;

    @Value("${producer.properties.acks}")
    private String acks;

    @Value("${producer.properties.retries}")
    private String retries;


    @ConditionalOnBean
    public ProducerFactory<String, User> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, key_serializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, value_serializer);
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        config.put(ProducerConfig.RETRIES_CONFIG, retries);

        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    public KafkaTemplate<String, User> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }

//        @Bean
//        public KafkaAdmin kafkaAdmin()
//        {
//        	Map<String, Object> config = new HashMap<>();
//        	config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        	return new KafkaAdmin(config);
//        }
//
//        @Bean
//        public NewTopic topic()
//        {
//        	return new NewTopic("abcdef",1,(short)1);
//        }
//

}



