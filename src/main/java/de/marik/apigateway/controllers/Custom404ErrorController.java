package de.marik.apigateway.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Custom404ErrorController {
	
//	@GetMapping("/error")
//	public String handleError(HttpServletRequest request) {
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//		
//        if (status != null) {
//            int statusCode = Integer.parseInt(status.toString());
//            if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                System.out.println("ERROR: inside");
//            	return "errors/not-found";
//            } 
//        }
//        
//        System.out.println("ERROR: outside");
//        return "errors/not-found";  // Default error page
//	}

}
