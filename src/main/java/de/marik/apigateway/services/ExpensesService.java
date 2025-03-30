package de.marik.apigateway.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.marik.apigateway.client.ExpensesClient;
import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.dto.ExpensesList;
import de.marik.apigateway.models.Person;
import de.marik.apigateway.utils.ApiNotAvailableException;
import de.marik.apigateway.utils.UnexpectedErrorException;

@Service
public class ExpensesService {

	private final ApiHealthService apiHealthService;
	private final ExpensesClient apiServiceClient;

	public ExpensesService(ApiHealthService apiHealthService, ExpensesClient apiServiceClient) {
		this.apiHealthService = apiHealthService;
		this.apiServiceClient = apiServiceClient;
	}

	public List<ExpensesDTO> getExpensesList(Person person) {
		if (!apiHealthService.isApiAvailable())
			throw new ApiNotAvailableException();
		ResponseEntity<ExpensesList> response = apiServiceClient.getExpensesByOwnerId(person.getId());
		if (response.getStatusCode() != HttpStatus.OK)
			throw new UnexpectedErrorException("The list of expenses could not be obtained from the API-server.");
		return response.getBody().getExpensesList();
	}
	
	public void deleteExpenses(int id) {
		if (!apiHealthService.isApiAvailable())
			throw new ApiNotAvailableException();
		ResponseEntity<String> response = apiServiceClient.deleteExpenses(id);
		if (response.getStatusCode() != HttpStatus.OK)
			throw new UnexpectedErrorException("The requested expenses could not be deleted.");
		
	}
	
	

}
