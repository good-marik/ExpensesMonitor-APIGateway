package de.marik.apigateway.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Custom404ErrorController implements ErrorController {
	
	// catching NOT FOUND and BAD REQUEST local errors
	@GetMapping("/error")
	public String handleError(HttpServletRequest request) {
        return "errors/general-error";
	}
}
