package de.marik.apigateway.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.marik.apigateway.models.Person;
import de.marik.apigateway.repositories.PersonRepository;

@Service
public class RegistrationService {
	private final PersonRepository personRepository;
	private final PasswordEncoder passwordEncoder;

	public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
		this.personRepository = personRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void register(Person person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		personRepository.save(person);
	}
}
