package de.marik.apigateway.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.marik.apigateway.models.Person;
import de.marik.apigateway.services.PersonService;
import de.marik.apigateway.utils.PersonValidatorForPasswords;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/person")
public class UpdatePersonController {
	private final PersonService personService;
	private final PersonValidatorForPasswords personValidatorForPasswords;

	public UpdatePersonController(PersonService personService,
			PersonValidatorForPasswords personValidatorForPasswords) {
		this.personService = personService;
		this.personValidatorForPasswords = personValidatorForPasswords;
	}

	@GetMapping("/edit")
	public String editPerson(Model model) {
		model.addAttribute("person", personService.getAuthentPerson());
		return "person/update";
	}

	@PatchMapping("/update")
	public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
		personValidatorForPasswords.validate(person, bindingResult);
		if (bindingResult.hasErrors())
			return "person/update";
		personService.updatePerson(person);
		return "redirect:/show";
	}

}
