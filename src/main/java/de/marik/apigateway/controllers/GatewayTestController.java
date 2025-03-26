package de.marik.apigateway.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.marik.apigateway.security.PersonDetails;

@Controller
public class GatewayTestController {

	@GetMapping("/hello")
	public String test(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
		model.addAttribute("person", personDetails.getPerson());
		return "hello";
	}
}
