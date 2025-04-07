package de.marik.apigateway.services;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.marik.apigateway.models.Person;
import de.marik.apigateway.repositories.PersonRepository;
import de.marik.apigateway.security.PersonDetails;

@Service
public class PersonService {
	private final PersonRepository personRepository;
	private final PasswordEncoder passwordEncoder;
	
	public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
		this.personRepository = personRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Optional<Person> getPersonByUsername(String username) {
		return personRepository.findByUsername(username);
	}
	
	public Person getAuthentPerson() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((PersonDetails) authentication.getPrincipal()).getPerson();
	}
	
	@Transactional
	public void updatePerson(Person person) {
		Person personToUpdate = getAuthentPerson();
		personToUpdate.setName(person.getName());
		personToUpdate.setPassword(passwordEncoder.encode(person.getPassword()));
		personRepository.save(personToUpdate);
	}
}
