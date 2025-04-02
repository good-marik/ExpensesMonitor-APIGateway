package de.marik.apigateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import de.marik.apigateway.config.FeignConfig;
import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.dto.ExpensesList;

@FeignClient(name = "api-service", url = "${custom.api.url}", configuration = FeignConfig.class)
public interface ExpensesClient {

	@GetMapping("/api/expenses")
	ResponseEntity<ExpensesList> getExpensesByOwnerId(@RequestParam int ownerId);

	@DeleteMapping("/api/delete")
	ResponseEntity<String> deleteExpenses(@RequestParam int id);

	@PostMapping("/api/create")
	ResponseEntity<ExpensesDTO> addExpenses(@RequestBody ExpensesDTO expensesDTO);

	@GetMapping("/api/expensesById")
	ResponseEntity<ExpensesDTO> getExpensesById(@RequestParam int id);

	@PostMapping("/api/update")
	ResponseEntity<ExpensesDTO> updateExpenses(@RequestBody ExpensesDTO expensesDTO);
}