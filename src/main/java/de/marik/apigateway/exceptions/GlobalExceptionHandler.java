package de.marik.apigateway.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.marik.apigateway.utils.ApiNotAvailableException;
import de.marik.apigateway.utils.UnexpectedErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ApiNotAvailableException.class)
	public String handleApiNotAvailable(ApiNotAvailableException e) {
		System.out.println(e.getMessage());
		return "redirect:/errors/api-down";
	}
	
    @ExceptionHandler(UnexpectedErrorException.class)	//TODO: to Rename to APIExeption or anything similar
    public String handleUnexpectedError(UnexpectedErrorException e, RedirectAttributes redirectAttributes) {
    	System.out.println(e.getMessage());
    	redirectAttributes.addFlashAttribute("message", e.getMessage());
        return "redirect:/errors/unexpectedError";
    }
}
