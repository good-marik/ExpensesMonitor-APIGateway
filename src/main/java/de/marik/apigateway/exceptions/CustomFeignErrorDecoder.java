package de.marik.apigateway.exceptions;

import java.io.IOException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomFeignErrorDecoder implements ErrorDecoder {

	// getting and customizing error message coming from Expenses Service
	@Override
	public Exception decode(String methodKey, Response response) {
		int statusCode = response.status();
		String errorMessage;

		try {
			errorMessage = response.body() != null ? new String(response.body().asInputStream().readAllBytes())
					: "Unknown error at Expenses Service";
		} catch (IOException e) {
			errorMessage = "Unknown error at Expenses Service";
		}
		return new ApiErrorException(statusCode + "  -  " + errorMessage);
	}
}