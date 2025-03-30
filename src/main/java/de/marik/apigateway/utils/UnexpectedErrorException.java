package de.marik.apigateway.utils;

public class UnexpectedErrorException extends RuntimeException {
	public UnexpectedErrorException(String message) {
		super(message);
	}
}
