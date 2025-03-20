package de.marik.apigateway.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.marik.apigateway.models.Person;
import de.marik.apigateway.services.PersonService;

@Component
public class PersonValidator implements Validator {

	private final PersonService personService;

	@Autowired
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
			errors.rejectValue("username", "", "A person with such username already exists! (my validator)");
	}
}
