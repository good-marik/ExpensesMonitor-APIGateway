package de.marik.apigateway.utils;

public class ApiNotAvailableException extends RuntimeException {
	public ApiNotAvailableException( ) {
		super("API-Server is currently not available");
	}
}
