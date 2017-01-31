package com.alexfrndz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrderTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderTaskApplication.class, args);
	}
}
