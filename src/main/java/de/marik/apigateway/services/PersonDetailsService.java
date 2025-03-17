package de.marik.apigateway.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import de.marik.apigateway.models.Person;
import de.marik.apigateway.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService {

	@Value("${temp.username}")
	private String username;

	@Value("${temp.password}")
	private String password;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO: working with DB

		// for testing purposes
		if (!username.equals(username)) {
			throw new UsernameNotFoundException("User " + username + " is not found!");
		}
		// dirty: many identical objects will be generated!
		Person person = new Person(username);
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		person.setPassword(encodedPassword);
		return new PersonDetails(person);
	}
}
