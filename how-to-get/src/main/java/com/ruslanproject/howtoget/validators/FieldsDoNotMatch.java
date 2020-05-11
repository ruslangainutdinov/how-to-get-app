package com.ruslanproject.howtoget.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = { FieldsDoNotMatchValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FieldsDoNotMatch {
	String message() default "Fields should not match";
	String first();
	String second();
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}