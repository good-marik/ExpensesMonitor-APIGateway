package de.marik.apigateway.services;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import de.marik.apigateway.models.Person;
import de.marik.apigateway.repositories.PersonRepository;
import de.marik.apigateway.security.PersonDetails;

@Service
public class PersonService {
	
	private final PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public Optional<Person> getPersonByUsername(String username) {
		return personRepository.findByUsername(username);
	}
	
	public Person getAuthentPerson() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((PersonDetails) authentication.getPrincipal()).getPerson();
	}

}
