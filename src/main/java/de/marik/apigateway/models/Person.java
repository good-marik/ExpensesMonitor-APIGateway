package de.marik.apigateway.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 1, max = 30, message = "Username should be between 1 and 30 symbols long")
	private String username;

	@Size(min = 4, max = 100, message = "Password should be at least 4 symbols long")
	private String password;
	
	@Size(min = 1, max = 30, message = "Name should be between 1 and 30 symbols long")
	private String name;
	
	@Transient
	private String passwordRepeat;
	
	//TODO: can be deleted?
	public Person(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Person() {
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

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//TODO: to delete! for debuggin only!
	@Override
	public String toString() {
		return "Person [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", passwordRepeat=" + passwordRepeat + "]";
	}
	
	
}
