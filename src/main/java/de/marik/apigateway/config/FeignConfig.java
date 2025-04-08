package de.marik.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.marik.apigateway.exceptions.CustomFeignErrorDecoder;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfig {
	
	//for exceptions while communicating with microservice Expenses
	@Bean
	ErrorDecoder errorDecoder() {
		return new CustomFeignErrorDecoder();
	}
}