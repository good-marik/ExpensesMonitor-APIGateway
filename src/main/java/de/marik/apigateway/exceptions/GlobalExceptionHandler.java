package de.marik.apigateway.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiNotAvailableException.class)
	public String handleApiNotAvailable(ApiNotAvailableException e) {
		return "errors/api-down";
	}

	@ExceptionHandler(ApiErrorException.class)
	public String handleApiError(ApiErrorException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "errors/api-error";
	}
	
	// catching other possible exceptions
	@ExceptionHandler(RuntimeException.class)
	public String runtime(RuntimeException e) {
		return "errors/general-error";
	}
}
