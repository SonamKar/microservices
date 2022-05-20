package com.example.kafkaproducer.controller;


import com.example.kafkaproducer.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.kafka.core.KafkaTemplate;

@RestController
public class UserController {

        @Autowired
        private KafkaTemplate<String, User> kafkaTemplate;

        private static final String TOPIC = "testProducer";

        @PostMapping("/publish")
        public String post(@RequestBody User user) {

            kafkaTemplate.send(TOPIC, user);

            return "Published successfully";
        }
    }

