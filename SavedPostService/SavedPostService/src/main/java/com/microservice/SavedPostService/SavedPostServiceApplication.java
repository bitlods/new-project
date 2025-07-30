package com.microservice.SavedPostService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SavedPostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavedPostServiceApplication.class, args);
	}

}
