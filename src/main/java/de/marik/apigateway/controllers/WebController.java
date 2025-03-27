package de.marik.apigateway.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.marik.apigateway.client.ApiServiceClient;
import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.models.Person;
import de.marik.apigateway.repositories.PersonRepository;
import de.marik.apigateway.security.PersonDetails;
import de.marik.apigateway.services.PersonDetailsService;
import de.marik.apigateway.services.PersonService;
import jakarta.validation.Valid;

@Controller
public class WebController {

    private final AuthController authController;

    private final PersonService personService;
	final static String dataServerURL = "http://localhost:8000";
	private final PersonRepository personRepository;
	private final PersonDetailsService personDetailsService;
	private ApiServiceClient apiServiceClient;

	public WebController(PersonRepository personRepository, PersonDetailsService personDetailsService,
			ApiServiceClient apiServiceClient, PersonService personService, AuthController authController) {
		this.personRepository = personRepository;
		this.personDetailsService = personDetailsService;
		this.apiServiceClient = apiServiceClient;
		this.personService = personService;
		this.authController = authController;
	}

	@GetMapping({ "/", "/home" })
	public String getHome() {
		return "home"; // no slash for production! :)
	}

	@GetMapping("/show")
	public String fetchData(Model model) {
		Person person = getAuthentPerson();
		List<ExpensesDTO> expensesList = apiServiceClient.getExpenses(person.getId()).getExpensesList();
		model.addAttribute("person", person);
		model.addAttribute("expensesList", expensesList);
		return "expenses/show";
	}

	@GetMapping("/create")
	public String newExpenses(@ModelAttribute("expenses") ExpensesDTO expensesDTO) {
//		expensesDTO.setDate(LocalDate.now());
		return "expenses/new";
	}

	@PostMapping("/create")
	public String createExpenses(@ModelAttribute("expenses") @Valid ExpensesDTO expensesDTO, BindingResult bindingResult) {
//		validator here???
		if (bindingResult.hasErrors()) {
			return "expenses/new";
		}
		expensesDTO.setOwnerIdentity(getAuthentPerson().getId());
		System.out.println("-".repeat(60));
		System.out.println(expensesDTO);
		ResponseEntity<String> response = apiServiceClient.addExpenses(expensesDTO);
		System.out.println(response.toString());
		System.out.println("-".repeat(60));
		return "redirect:/show";
	}

//	@GetMapping("/hello")
//	public String test(Model model) {
//		Person person = getAuthentPerson();
//		model.addAttribute("person", person);
//		return "hello";
//	}

//	@GetMapping("/showTemp")
//	public String showExpenses(Model model) {
//		Person person = getAuthentPerson();
//		RestTemplate restTemplate = new RestTemplate();
//		String url = dataServerURL + "/api/getExpenses?id=" + person.getId();
//		ExpensesList response = restTemplate.getForObject(url, ExpensesList.class);
//		List<ExpensesDTO> expensesList = response.getExpensesList();
//		model.addAttribute("person", person);
//		model.addAttribute("expensesList", expensesList);
//		System.out.println(expensesList);
//		return "expenses/show";
//	}

	private Person getAuthentPerson() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((PersonDetails) authentication.getPrincipal()).getPerson();
	}
}
