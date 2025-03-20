package de.marik.apigateway.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

//	@NotBlank(message = "Your name should not be empty")
//	@Size(min = 1, max = 100, message = "Your name should not be longer than 100 symbols")
//	private String name;

	@NotBlank(message = "Username should not be empty")
	@Size(min = 1, max = 100, message = "Username should not be longer than 100 symbols")
	private String username;

	// TODO: validation for a plain password here
	@NotBlank(message = "Your password should not be empty")
	private String password;

	public Person(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// is really needed for Spring?
	public Person() {
	}

	// for testing ONLY. Should be deleted afterwards!
	public Person(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
