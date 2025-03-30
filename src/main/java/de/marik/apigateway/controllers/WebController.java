package de.marik.apigateway.controllers;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.marik.apigateway.client.ExpensesClient;
import de.marik.apigateway.dto.ExpensesDTO;
import de.marik.apigateway.models.Person;
import de.marik.apigateway.security.PersonDetails;
import de.marik.apigateway.services.ExpensesService;
import de.marik.apigateway.services.PersonService;
import jakarta.validation.Valid;

@Controller
public class WebController {

	private final ExpensesClient apiServiceClient;
	private final PersonService personService;
	private final ExpensesService expensesService;

	public WebController(ExpensesClient apiServiceClient, PersonService personService,
			ExpensesService expensesService) {
		this.apiServiceClient = apiServiceClient;
		this.personService = personService;
		this.expensesService = expensesService;
	}

	@GetMapping({ "/", "/home" })
	public String getHome() {
		return "home";
	}

	@GetMapping("/show")
	public String fetchExpenses(Model model) {
		Person person = personService.getAuthentPerson();
		model.addAttribute("person", person);
		model.addAttribute("expensesList", expensesService.getExpensesList(person));
		return "expenses/show";
	}

	@PostMapping("/delete")
	public String deleteExpenses(@RequestParam int id) {
		expensesService.deleteExpenses(id);
		return "redirect:/show";
	}

	@GetMapping("/create")
	public String newExpenses(@ModelAttribute("expenses") ExpensesDTO expensesDTO) {
		expensesDTO.setDate(LocalDate.now());
		return "expenses/new";
	}

	@PostMapping("/create")
	public String createExpenses(@ModelAttribute("expenses") @Valid ExpensesDTO expensesDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "expenses/new";
		expensesService.create(expensesDTO);
		return "redirect:/show";
	}

//	@PostMapping("/create")
//	public String createExpenses(@ModelAttribute("expenses") @Valid ExpensesDTO expensesDTO,
//			BindingResult bindingResult) {
////		TODO: validator here???
//		if (bindingResult.hasErrors()) {
//			return "expenses/new";
//		}
//		expensesDTO.setOwnerIdentity(getAuthentPerson().getId());
//		System.out.println("-".repeat(60));
//		System.out.println(expensesDTO);
//		ResponseEntity<String> response = apiServiceClient.addExpenses(expensesDTO);
//		System.out.println(response.toString());
//		System.out.println("-".repeat(60));
//		return "redirect:/show";
//	}

	@GetMapping("/edit")
	public String editExpenses(@RequestParam int id, Model model) {
		model.addAttribute("expenses", apiServiceClient.getExpensesById(id));
		return "expenses/update";
	}

	@PatchMapping("/update")
	public String updateExpenses(@ModelAttribute("expense") @Valid ExpensesDTO expensesDTO,
			BindingResult bindingResult) {
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

	// TODO: to delete from here!
	public Person getAuthentPerson() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((PersonDetails) authentication.getPrincipal()).getPerson();
	}

}
