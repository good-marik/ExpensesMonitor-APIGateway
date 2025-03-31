package de.marik.apigateway.exceptions;

public class ApiNotAvailableException extends RuntimeException {
	public ApiNotAvailableException( ) {
		super("API-Server is currently not available");
	}
}
