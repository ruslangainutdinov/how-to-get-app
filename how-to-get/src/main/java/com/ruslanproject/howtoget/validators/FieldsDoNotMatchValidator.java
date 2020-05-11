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
		first=constraintAnnotation.first();
		second=constraintAnnotation.second();
		message=constraintAnnotation.message();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = false;		
		BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
		String passwordFirst=(String) wrapper.getPropertyValue(first);
		String passwordSecond=(String) wrapper.getPropertyValue(second);
		if(!passwordFirst.equals(passwordSecond)) {
			valid=true;
		}
		return valid;
	}

}
