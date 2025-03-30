package de.marik.apigateway.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.marik.apigateway.client.ApiHealthClient;

@Service
public class ApiHealthService {
	
	@Autowired
	private ApiHealthClient apiHealthClient;
	
	public boolean isApiAvailable() {
		try {
			ResponseEntity<Map<String, Object>> response = apiHealthClient.checkHealth();
			return response.getStatusCode() == HttpStatus.OK && "UP".equals(response.getBody().get("status"));
		} catch (Exception e) {
			System.err.println("API is not available! " + e.getMessage());
			return false;
		}
	}
}
