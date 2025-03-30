package de.marik.apigateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.dto.ExpensesList;
import de.marik.apigateway.models.Expenses;

@FeignClient(name = "api-service", url = "http://localhost:8000")
public interface ExpensesClient {
	
	@GetMapping("/api/expenses")
	ResponseEntity<ExpensesList> getExpensesByOwnerId(@RequestParam int ownerId);

	@DeleteMapping("/api/delete")
	ResponseEntity<String> deleteExpenses(@RequestParam int id);
	
	@PostMapping("/api/create")
	ResponseEntity<Expenses> addExpenses(@RequestBody ExpensesDTO expensesDTO);
	
	@GetMapping("/api/expensesById")
	ResponseEntity<ExpensesDTO> getExpensesById(@RequestParam int id);
	
	//TODO: proper response here!
	@PostMapping("/api/updateExpenses")
	void updateExpenses(@RequestBody ExpensesDTO expensesDTO);
	
}