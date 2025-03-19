package de.marik.apigateway.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.marik.apigateway.models.Person;
import de.marik.apigateway.repositories.PersonRepository;
import de.marik.apigateway.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService {
	
	private final PersonRepository personRepository;
	
	public PersonDetailsService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> person = personRepository.findByUsername(username);
		if(person.isEmpty()) throw new UsernameNotFoundException("User was not found! :(((");
		return new PersonDetails(person.get());
	}
}