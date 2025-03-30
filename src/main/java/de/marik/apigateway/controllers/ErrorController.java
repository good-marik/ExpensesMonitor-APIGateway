package de.marik.apigateway.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/errors")
public class ErrorController {
	
	@GetMapping("/unexpectedError")
	public String unexpectedError(@RequestParam(required = false) String message, Model model) {
		model.addAttribute("message", message);
		return "errors/unexpectedError"; 
	}
	
	@GetMapping("/api-down")
	public String apiDown() {
		return "errors/api-down"; 
	}

}
