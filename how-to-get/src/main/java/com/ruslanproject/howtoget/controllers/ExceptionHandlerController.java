package com.ruslanproject.howtoget.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ruslanproject.howtoget.utils.ApplicationViews;

/**
 * Exception handler controller
 * 
 * @author Ruslan Gainutdinov
 *
 */

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(Exception.class)
	public String globalExceptionHandler() {
		return ApplicationViews.DEFAULT_EXCEPTION_HANDLER_VIEW;
	}
}
