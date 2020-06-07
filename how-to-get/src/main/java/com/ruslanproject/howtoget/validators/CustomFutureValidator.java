package com.ruslanproject.howtoget.validators;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomFutureValidator implements ConstraintValidator<CustomFutureDateTime,String> {

	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = false;
		LocalDateTime valueDateTime = LocalDateTime.parse(value);
		/*LocalDateTime valueDateTime = LocalDateTime.parse(value);*/
		if(valueDateTime.isAfter(LocalDateTime.now())) {
			isValid=true;
		}
		
		return isValid;
	}

}
