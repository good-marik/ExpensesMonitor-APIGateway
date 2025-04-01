package de.marik.apigateway.controllers;

import java.io.IOException;

import de.marik.apigateway.exceptions.ApiErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomFeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		int statusCode = response.status();
		String errorMessage;
		
		try {
			errorMessage = response.body() != null ? new String(response.body().asInputStream().readAllBytes())
					: "Unknown error";
		} catch (IOException e) {
			errorMessage = "Unknown error";
		}
		return new ApiErrorException(statusCode + "  -  " + errorMessage);
	}
}