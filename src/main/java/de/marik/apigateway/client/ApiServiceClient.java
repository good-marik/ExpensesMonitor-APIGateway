package de.marik.apigateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.dto.ExpensesList;

@FeignClient(name = "api-service", url = "http://localhost:8000")
public interface ApiServiceClient {
	@GetMapping("/api/getExpenses")
	ExpensesList getExpensesByOwnerId(@RequestParam int id);

	@PostMapping("/api/addExpenses")
	ResponseEntity<String> addExpenses(@RequestBody ExpensesDTO expensesDTO);
	
	@DeleteMapping("/api/deleteExpenses")
	//TODO: proper response here!
	void deleteExpenses(@RequestParam int id);
	
	//TODO: proper response here!
	@PostMapping("/api/updateExpenses")
	void updateExpenses(@RequestBody ExpensesDTO expensesDTO);
	
	@GetMapping("/api/getExpensesById")
	ExpensesDTO getExpensesById(@RequestParam int id);
}