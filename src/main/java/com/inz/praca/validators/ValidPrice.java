package com.inz.praca.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriceValidator.class)
@Documented
public @interface ValidPrice {

  String message() default "Nieprawidłowo podana cena";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
