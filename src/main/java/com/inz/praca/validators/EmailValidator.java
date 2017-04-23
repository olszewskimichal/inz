package com.inz.praca.validators;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    public static final String EMAIL_PATTERN = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    @Override
    public void initialize(ValidEmail value) {
        log.debug("Poprawnie zainicjalizowano Validator");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
