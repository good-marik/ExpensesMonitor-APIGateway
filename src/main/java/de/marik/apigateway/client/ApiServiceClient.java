package de.marik.apigateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.dto.ExpensesList;

@FeignClient(name = "api-service", url = "http://localhost:8000")
public interface ApiServiceClient {
	@GetMapping("/api/getExpenses")
	ExpensesList getExpenses(@RequestParam int id);

	@PostMapping("/api/addExpenses")
	ResponseEntity<String> addExpenses(@RequestBody ExpensesDTO expensesDTO);
}