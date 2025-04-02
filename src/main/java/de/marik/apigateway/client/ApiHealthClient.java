package de.marik.apigateway.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "health-service", url = "${custom.api.url}")
public interface ApiHealthClient {
	
	@GetMapping("/actuator/health")
	ResponseEntity<Map<String, Object>> checkHealth();
}
