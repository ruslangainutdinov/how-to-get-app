package com.ruslanproject.howtoget.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=CustomFutureValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomFutureDateTime {
	String message() default "Date must be in future";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	
}
