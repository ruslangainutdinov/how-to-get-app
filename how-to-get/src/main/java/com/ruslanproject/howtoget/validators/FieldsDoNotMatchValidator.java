package com.ruslanproject.howtoget.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldsDoNotMatchValidator implements ConstraintValidator<FieldsDoNotMatch,Object>{

	private String first;
	
	private String second;
	
	private String message;
	
	@Override
	public void initialize(FieldsDoNotMatch constraintAnnotation) {
		this.first=constraintAnnotation.first();
		this.second=constraintAnnotation.second();
		this.message=constraintAnnotation.message();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = false;		
		BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
		String firstString=(String) wrapper.getPropertyValue(first);
		String secondString=(String) wrapper.getPropertyValue(second);
		if(!firstString.equals(secondString)) {
			valid=true;
		}
		return valid;
	}

}
