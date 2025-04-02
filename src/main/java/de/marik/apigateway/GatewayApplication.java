package de.marik.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
@EnableFeignClients
public class GatewayApplication {
	
	public static void main(String[] args) {
		System.out.println("Staring Gateway......");
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	// custom messages in messages.properties
//	@Bean
//	public MessageSource messageSource() {
//		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//		messageSource.setBasename("classpath:messages");
//		messageSource.setDefaultEncoding("UTF-8");
//		return messageSource;
//	}
}
