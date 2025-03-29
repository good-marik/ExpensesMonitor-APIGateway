package de.marik.apigateway.controllers;

import java.util.List;

import javax.naming.Binding;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import de.marik.apigateway.GatewayApplication;
import de.marik.apigateway.client.ApiServiceClient;
import de.marik.apigateway.config.SecurityConfig;
import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.models.Person;
import de.marik.apigateway.repositories.PersonRepository;
import de.marik.apigateway.security.PersonDetails;
import de.marik.apigateway.services.PersonDetailsService;
import de.marik.apigateway.services.PersonService;
import de.marik.apigateway.services.RegistrationService;
import de.marik.apigateway.util.PersonValidator;
import jakarta.validation.Valid;

@Controller
public class WebController {

    private final RegistrationService registrationService;

    private final PasswordEncoder getPasswordEncoder;

    private final SecurityConfig securityConfig;

    private final GatewayApplication gatewayApplication;

	private final PersonValidator personValidator;

	private final AuthController authController;

	private final PersonService personService;
	final static String dataServerURL = "http://localhost:8000";
	private final PersonRepository personRepository;
	private final PersonDetailsService personDetailsService;
	private ApiServiceClient apiServiceClient;

	public WebController(PersonRepository personRepository, PersonDetailsService personDetailsService,
			ApiServiceClient apiServiceClient, PersonService personService, AuthController authController,
			PersonValidator personValidator, GatewayApplication gatewayApplication, SecurityConfig securityConfig, PasswordEncoder getPasswordEncoder, RegistrationService registrationService) {
		this.personRepository = personRepository;
		this.personDetailsService = personDetailsService;
		this.apiServiceClient = apiServiceClient;
		this.personService = personService;
		this.authController = authController;
		this.personValidator = personValidator;
		this.gatewayApplication = gatewayApplication;
		this.securityConfig = securityConfig;
		this.getPasswordEncoder = getPasswordEncoder;
		this.registrationService = registrationService;
	}

	@GetMapping({ "/", "/home" })
	public String getHome() {
		return "home"; // no slash for production! :)
	}

	@GetMapping("/delete")
	public String delete(@RequestParam int id) {
		apiServiceClient.deleteExpenses(id);
		return "redirect:/show";
	}

	@GetMapping("/show")
	public String fetchData(Model model) {
		Person person = getAuthentPerson();
		List<ExpensesDTO> expensesList = apiServiceClient.getExpensesByOwnerId(person.getId()).getExpensesList();
		model.addAttribute("person", person);
		model.addAttribute("expensesList", expensesList);
		return "expenses/show";
	}

	@GetMapping("/edit")
	public String editExpenses(@RequestParam int id, Model model) {
		model.addAttribute("expenses", apiServiceClient.getExpensesById(id));
		return "expenses/update";
	}

	@PatchMapping("/update")
	public String updateExpenses(@ModelAttribute("expense") @Valid ExpensesDTO expensesDTO, BindingResult bindingResult) {
		// TODO: validator here
		if (bindingResult.hasErrors()) {
			return "edit";
		}
		
		System.out.println("-".repeat(60));
		System.out.println("id for the owner = " + expensesDTO.getOwnerIdentity());
		System.out.println("-".repeat(60));
		apiServiceClient.updateExpenses(expensesDTO);
		return "redirect:/show";
	}

	@GetMapping("/create")
	public String newExpenses(@ModelAttribute("expenses") ExpensesDTO expensesDTO) {
//		expensesDTO.setDate(LocalDate.now());
		return "expenses/new";
	}

	@PostMapping("/create")
	public String createExpenses(@ModelAttribute("expenses") @Valid ExpensesDTO expensesDTO,
			BindingResult bindingResult) {
//		TODO: validator here???
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
