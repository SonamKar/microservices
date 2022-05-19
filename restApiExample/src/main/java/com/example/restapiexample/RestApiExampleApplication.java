package com.example.restapiexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class RestApiExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiExampleApplication.class, args);
	}

}
