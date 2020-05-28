package com.ruslanproject.howtoget.validators;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FutureDateTimeStringValidator implements ConstraintValidator<FutureDateTimeString,Object>{

	private String first;
	
	private String second;
	
	private String message;
	
	@Override
	public void initialize(FutureDateTimeString constraintAnnotation) {
		first=constraintAnnotation.first();
		second=constraintAnnotation.second();
		message=constraintAnnotation.message();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = false;		
		BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
		String departure=(String) wrapper.getPropertyValue(first);
		String arrival=(String) wrapper.getPropertyValue(second);
		LocalDateTime departureDateTime=LocalDateTime.parse(departure);
		LocalDateTime arrivalDateTime=LocalDateTime.parse(arrival);
		
		if(departureDateTime.isBefore(arrivalDateTime)) {
			valid=true;
		}
		return valid;
	}

}
