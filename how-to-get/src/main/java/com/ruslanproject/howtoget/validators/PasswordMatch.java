package com.ruslanproject.howtoget.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*@PasswordMatch validator for password matching during registration*/
@Constraint(validatedBy=PasswordMatchValidator.class)
@Target(value = { ElementType.TYPE })//maybe add ANNOTATION_TYPE
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
	String message() default "Fields must match";
	String first();
	String second();
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}