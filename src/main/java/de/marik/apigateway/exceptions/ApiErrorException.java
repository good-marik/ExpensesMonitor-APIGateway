package de.marik.apigateway.exceptions;

public class ApiErrorException extends RuntimeException {
	public ApiErrorException(String message) {
		super("API error: " + message);
	}
}
