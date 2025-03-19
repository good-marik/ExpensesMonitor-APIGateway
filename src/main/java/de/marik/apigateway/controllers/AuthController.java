package de.marik.apigateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marik.apigateway.models.Person;
import de.marik.apigateway.services.RegistrationService;
import de.marik.apigateway.util.PersonValidator;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private final PersonValidator personValidator;
	private final RegistrationService registrationService;

	@Autowired
	public AuthController(PersonValidator personValidator, RegistrationService registrationService) {
		this.personValidator = personValidator;
		this.registrationService = registrationService;
	}

	@GetMapping("/login")
	public String loginPage() {
		return "auth/login";
	}

	@GetMapping("/registration")
	public String registrationPage(@ModelAttribute("person") Person person) {
		return "auth/registration";
	}

	@PostMapping("/registration")
	public String performResistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);

		if (bindingResult.hasErrors())
			return "/auth/registration";

		registrationService.register(person);
		return "redirect:/auth/login";
	}
}
