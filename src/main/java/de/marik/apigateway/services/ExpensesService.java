package de.marik.apigateway.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.marik.apigateway.client.ExpensesClient;
import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.dto.ExpensesList;
import de.marik.apigateway.models.Expenses;
import de.marik.apigateway.models.Person;
import de.marik.apigateway.security.PersonDetails;
import de.marik.apigateway.utils.ApiNotAvailableException;
import de.marik.apigateway.utils.UnexpectedErrorException;
import jakarta.validation.Valid;

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
		if (!apiHealthService.isApiAvailable())
			throw new ApiNotAvailableException();
		ResponseEntity<ExpensesList> response = apiServiceClient.getExpensesByOwnerId(person.getId());
		if (response.getStatusCode() != HttpStatus.OK)
			throw new UnexpectedErrorException("API error: The list of expenses could not be obtained.");
		return response.getBody().getExpensesList();
	}
	
	@Transactional
	public void deleteExpenses(int id) {
		if (!apiHealthService.isApiAvailable())
			throw new ApiNotAvailableException();
		ResponseEntity<String> response = apiServiceClient.deleteExpenses(id);
		if (response.getStatusCode() != HttpStatus.OK)
			throw new UnexpectedErrorException("API error: The requested expenses could not be deleted.");
	}

	@Transactional
	public void create(ExpensesDTO expensesDTO) {
		if (!apiHealthService.isApiAvailable())		//TODO: export into separate method
			throw new ApiNotAvailableException();
		expensesDTO.setOwnerIdentity(getAuthentPerson().getId());
		ResponseEntity<Expenses> response = apiServiceClient.addExpenses(expensesDTO);
		if(response.getStatusCode() != HttpStatus.CREATED)
			throw new UnexpectedErrorException("API error: The current expenses could not be added.");
	}
	
	@Transactional
	public void update(ExpensesDTO expensesDTO) {
		if (!apiHealthService.isApiAvailable())		//TODO: export into separate method
			throw new ApiNotAvailableException();
		ResponseEntity<Expenses> response = apiServiceClient.updateExpenses(expensesDTO);
		if(response.getStatusCode() != HttpStatus.OK)
			throw new UnexpectedErrorException("API error: The given expenses could not be updated.");
	}

	public ExpensesDTO getExpensesById(int id) {
		if (!apiHealthService.isApiAvailable())		//TODO: export into separate method
			throw new ApiNotAvailableException();
		ResponseEntity<ExpensesDTO> response = apiServiceClient.getExpensesById(id);
		if(response.getStatusCode() != HttpStatus.OK)
			throw new UnexpectedErrorException("API error: This expenses could not be obtained.");
		return response.getBody();
	}
	
	private Person getAuthentPerson() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((PersonDetails) authentication.getPrincipal()).getPerson();
	}
	
}
