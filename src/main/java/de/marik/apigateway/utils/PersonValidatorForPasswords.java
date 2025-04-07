package de.marik.apigateway.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.marik.apigateway.models.Person;

@Component
public class PersonValidatorForPasswords implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		if(!person.getPassword().equals(person.getPasswordRepeat())) {
			errors.rejectValue("password", "", "Passwords do not match!");
		}
		if(person.getUsername().equals("guest")) {
			errors.rejectValue("username", "", "The demo account cannot be modified. Please, create a new account");
		}
		
	}
}
