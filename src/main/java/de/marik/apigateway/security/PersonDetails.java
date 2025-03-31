package de.marik.apigateway.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.marik.apigateway.models.Person;

public class PersonDetails implements UserDetails {
	private final Person person;

	public PersonDetails(Person person) {
		this.person = person;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Custom authorization here if needed!
		return null;
	}

	@Override
	public String getPassword() {
		return person.getPassword();
	}

	@Override
	public String getUsername() {
		return person.getUsername();
	}

	public Person getPerson() {
		return person;
	}

}
