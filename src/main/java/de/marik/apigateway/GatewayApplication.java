package de.marik.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {
	public static void main(String[] args) {
		System.out.println("Staring Gateway......");
		SpringApplication.run(GatewayApplication.class, args);
	}
}
