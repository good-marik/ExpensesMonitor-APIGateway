package de.marik.apigateway.utils;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.marik.apigateway.models.Person;
import de.marik.apigateway.services.PersonService;

@Component
public class PersonValidator implements Validator {

	private final PersonService personService;

	public PersonValidator(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		Optional<Person> personInDB = personService.getPersonByUsername(person.getUsername());
		if (personInDB.isPresent())
			errors.rejectValue("username", "", "This username is already in use");
		if(!person.getPassword().equals(person.getPasswordRepeat())) {
			errors.rejectValue("password", "", "Passwords do not match!");
		}
	}
}
