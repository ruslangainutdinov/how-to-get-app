package com.ruslanproject.howtoget.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler controller
 * 
 * @author Ruslan Gainutdinov
 *
 */

@ControllerAdvice
public class ExceptionHandlerController {
	
	private final static String DEFAULT_VIEW ="error";
	
	@ExceptionHandler(Exception.class)
	public String globalExceptionHandler() {
		return DEFAULT_VIEW;
	}
}
