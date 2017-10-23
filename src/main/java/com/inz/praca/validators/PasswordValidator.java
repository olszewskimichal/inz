package com.inz.praca.validators;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

@Slf4j
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    public static final String PASS_REGEX_PATTERN = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}$";
    private final Pattern pattern = compile(PasswordValidator.PASS_REGEX_PATTERN);

    @Override
    public void initialize(ValidPassword value) {
        PasswordValidator.log.debug("Poprawnie zainicjalizowano Validator");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
