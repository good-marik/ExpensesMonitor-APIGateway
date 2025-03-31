package de.marik.apigateway.controllers;

import java.io.IOException;

import de.marik.apigateway.exceptions.ApiErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomFeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {

		String errorMessage = response.status() + "  -  ";
		try {
			errorMessage += response.body() != null ? new String(response.body().asInputStream().readAllBytes())
					: "Unknown error";
		} catch (IOException e) {
			errorMessage += "Unknown error";
		}
		return new ApiErrorException(errorMessage);

//        HttpStatus status = HttpStatus.valueOf(response.status());
//
//        try {
//            String errorMessage = response.body() != null ? new String(response.body().asInputStream().readAllBytes()) : "Unknown error";
//            
//            switch (status) {
//                case NOT_FOUND:
//                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found: " + errorMessage);
//                case BAD_REQUEST:
//                	System.out.println(errorMessage);
//                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request: " + errorMessage);
//                case INTERNAL_SERVER_ERROR:
//                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + errorMessage);
//                default:
//                    return new ResponseStatusException(status, "Unexpected error: " + errorMessage);
//            }
//        } catch (IOException e) {
//            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing error response", e);
//        }
	}
}