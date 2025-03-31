package de.marik.apigateway.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiNotAvailableException.class)
	public String handleApiNotAvailable(ApiNotAvailableException e) {
		return "redirect:/errors/api-down";
	}

	@ExceptionHandler(ResponseStatusException.class)
	public String responoseStatus(ResponseStatusException e, Model model) {
		System.out.println(e.getStatusCode().value());
		System.out.println(e.getReason());
		
		model.addAttribute("message", e.getReason());
		model.addAttribute("errorCode", e.getStatusCode().value());
		return "errors/api-error";
	}

	@ExceptionHandler(ApiErrorException.class)
	public String handleApiError(ApiErrorException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "errors/api-error";
	}
}
