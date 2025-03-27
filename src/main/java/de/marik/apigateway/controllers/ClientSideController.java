package de.marik.apigateway.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.dto.ExpensesList;
import de.marik.apigateway.models.Person;
import de.marik.apigateway.repositories.PersonRepository;
import de.marik.apigateway.security.PersonDetails;
import de.marik.apigateway.services.PersonDetailsService;

@Controller
public class ClientSideController {
	final static String dataServerURL = "http://localhost:8000";
	private final PersonRepository personRepository;
	private final PersonDetailsService personDetailsService;

	ClientSideController(PersonDetailsService personDetailsService, PersonRepository personRepository) {
		this.personDetailsService = personDetailsService;
		this.personRepository = personRepository;
	}

	@GetMapping("/hello")
	public String test(Model model) {
		Person person = getAuthentPerson();
		model.addAttribute("person", person);
		return "hello";
	}

	@GetMapping("/show")
	public String showExpenses(Model model) {
		Person person = getAuthentPerson();
		RestTemplate restTemplate = new RestTemplate();
		String url = dataServerURL + "/api/getExpenses?id=" + person.getId();
		ExpensesList response = restTemplate.getForObject(url, ExpensesList.class);
		List<ExpensesDTO> expensesList = response.getExpensesList();

		model.addAttribute("person", person);
		model.addAttribute("expensesList", expensesList);
		
		System.out.println(expensesList);

		return "expenses/show";
	}

	private Person getAuthentPerson() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((PersonDetails) authentication.getPrincipal()).getPerson();
	}
}
