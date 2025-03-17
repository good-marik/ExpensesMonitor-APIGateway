package de.marik.apigateway.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.marik.apigateway.security.PersonDetails;

@RestController
//@RequestMapping("/test")
public class APIGatewayController {
	
	@GetMapping("/test")
	public String test() {
		System.out.println("RestController is READY!");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails =  (PersonDetails) authentication.getPrincipal();
		String name = personDetails.getUsername();
		
		return "Privet, " + name + "!";
	}
	
}
