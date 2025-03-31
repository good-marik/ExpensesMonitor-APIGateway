package de.marik.apigateway.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.marik.apigateway.client.ExpensesClient;
import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.dto.ExpensesList;
import de.marik.apigateway.exceptions.ApiNotAvailableException;
import de.marik.apigateway.models.Person;
import de.marik.apigateway.security.PersonDetails;

@Service
@Transactional(readOnly = true)
public class ExpensesService {
	private final ApiHealthService apiHealthService;
	private final ExpensesClient apiServiceClient;

	public ExpensesService(ApiHealthService apiHealthService, ExpensesClient apiServiceClient) {
		this.apiHealthService = apiHealthService;
		this.apiServiceClient = apiServiceClient;
	}

	public List<ExpensesDTO> getExpensesList(Person person) {
		checkIfApiAvailable();
		ResponseEntity<ExpensesList> response = apiServiceClient.getExpensesByOwnerId(person.getId());
//		if (response.getStatusCode() != HttpStatus.OK)
//			throw new ApiErrorException("The list of expenses could not be obtained.");
		return response.getBody().getExpensesList();
	}
	
	public ExpensesDTO getExpensesById(int id) {
		checkIfApiAvailable();
		ResponseEntity<ExpensesDTO> response = apiServiceClient.getExpensesById(id);
//		if(response.getStatusCode() != HttpStatus.OK)
//			throw new ApiErrorException("This expenses could not be obtained.");
		return response.getBody();
	}
	
	@Transactional
	public void deleteExpenses(int id) {
		checkIfApiAvailable();
		ResponseEntity<String> response = apiServiceClient.deleteExpenses(id);
//		if (response.getStatusCode() != HttpStatus.OK)
//			throw new ApiErrorException("The requested expenses could not be deleted.");
	}

	@Transactional
	public void create(ExpensesDTO expensesDTO) {
		checkIfApiAvailable();
		expensesDTO.setOwnerIdentity(getAuthentPerson().getId());
		ResponseEntity<ExpensesDTO> response = apiServiceClient.addExpenses(expensesDTO);
//		if(response.getStatusCode() != HttpStatus.CREATED)
//			throw new ApiErrorException("The current expenses could not be added.");
	}
	
	@Transactional
	public void update(ExpensesDTO expensesDTO) {
		checkIfApiAvailable();
		ResponseEntity<ExpensesDTO> response = apiServiceClient.updateExpenses(expensesDTO);
//		if(response.getStatusCode() != HttpStatus.OK)
//			throw new ApiErrorException("The given expenses could not be updated.");
	}

	private void checkIfApiAvailable() {
		if (!apiHealthService.isApiAvailable())
			throw new ApiNotAvailableException();
	}
	
	private Person getAuthentPerson() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((PersonDetails) authentication.getPrincipal()).getPerson();
	}
	
}
