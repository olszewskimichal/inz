package com.inz.praca.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriceValidator.class)
@Documented
public @interface ValidPrice {
    String message() default "Nieprawidłowo podana cena";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
