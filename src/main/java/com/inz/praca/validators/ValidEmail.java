package com.inz.praca.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
	String message() default "{ValidEmail}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}