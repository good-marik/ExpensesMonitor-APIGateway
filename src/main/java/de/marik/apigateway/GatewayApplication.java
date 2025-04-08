package de.marik.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GatewayApplication {
	
	public static void main(String[] args) {
		System.out.println("Staring Gateway......");
		SpringApplication.run(GatewayApplication.class, args);
	}
	
}
