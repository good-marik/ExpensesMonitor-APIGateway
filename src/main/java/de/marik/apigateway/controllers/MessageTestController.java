package de.marik.apigateway.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageTestController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/test")
    public String getMessage(@RequestParam(defaultValue = "en") String lang) {
        System.out.println("in test controller!");
    	@SuppressWarnings("deprecation")
		Locale locale = new Locale(lang);
        return messageSource.getMessage("typeMismatch.expensesDTO.amount", null, locale);
    }
}