package com.example.kafkaconsumer.config;

import com.example.kafkaconsumer.domain.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerConfiguration {

    @Value("${consumer.bootstrap-servers}")
    private String bootstrap_servers;

    @Value("${consumer.key-serializer}")
    private String key_serializer;

    @Value("${consumer.consumer-group}")
    private String consumer_group;

    @Value("${consumer.reset-config}")
    private String reset_config;

    @Bean
    public ConsumerFactory<String, User> consumerFactory() {
    	
    	JsonDeserializer<User> deserializer=new JsonDeserializer<>(User.class);
    	deserializer.setRemoveTypeHeaders(false);
    	deserializer.addTrustedPackages("*");
    	deserializer.setUseTypeMapperForKey(true);
    	
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumer_group);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, reset_config);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, key_serializer);
        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),deserializer);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
