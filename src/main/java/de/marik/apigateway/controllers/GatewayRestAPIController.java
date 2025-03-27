package de.marik.apigateway.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.dto.ExpensesList;
import de.marik.apigateway.models.Person;
import de.marik.apigateway.security.PersonDetails;

@RestController
public class GatewayRestAPIController {
	// to be moved to env.var.
	final static String dataServerURL = "http://localhost:8000";

	@GetMapping("/showExpenses")
	public ExpensesList showExpenses() {
		Person person = getAuthentPerson();
		RestTemplate restTemplate = new RestTemplate();
		String url = dataServerURL + "/api/getExpenses?id=" + person.getId();
		ExpensesList response = restTemplate.getForObject(url, ExpensesList.class);
		return response;
	}

	// for tesing, then switch to PostMapping and working with forms
	@GetMapping("/newExpenses")
	public String addExpenses() {
		Person person = getAuthentPerson();
		
		// for testing, later from forms!
		ExpensesDTO expenses = new ExpensesDTO();
		expenses.setAmount(1234.56);
		expenses.setComment("Beta");
		expenses.setDate(LocalDate.now());
		expenses.setOwnerIdentity(person.getId());
		System.out.println(expenses);

		Map<String, Object> json = new HashMap<>();
		json.put("amount", expenses.getAmount());
		json.put("date", expenses.getDate());
		json.put("comment", expenses.getComment());
		json.put("ownerIdentity", person.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(json, headers);

		RestTemplate restTemplate = new RestTemplate();
		String url = dataServerURL + "/api/addExpenses";

		
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
			System.out.println("DataServer responded: " + response);
			System.out.println("-".repeat(20));
			System.out.println("Status Code: " + response.getStatusCode());
			System.out.println("Headers: " + response.getHeaders());
			System.out.println("Response Body: " + response.getBody());
			System.out.println("-".repeat(20));
			return (response.getBody());
		} catch (RestClientException e) {
			System.out.println("BAD REQUEST ".repeat(3));
			System.out.println(e.getMessage());
			return(e.getMessage());
		}

//		some examples
		
//		ResponseEntity<String> response = restTemplate.postForEntity("https://example.com/api", request, String.class);
//		System.out.println(response.getBody());
		
//        try {
//            restTemplate.postForObject(url, request, String.class);
//
//            System.out.println("Измерение успешно отправлено на сервер!");
//        } catch (HttpClientErrorException e) {
//            System.out.println("ОШИБКА!");
//            System.out.println(e.getMessage());
//        }

		
	}

	private Person getAuthentPerson() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((PersonDetails) authentication.getPrincipal()).getPerson();
	}
}
