package de.marik.apigateway.exceptions;

public class ApiErrorException extends RuntimeException {
	public ApiErrorException(String message) {
		super(message);
	}
}
