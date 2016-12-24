package com.inz.praca.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

	public static final String PASS_REGEX_PATTERN = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{3,}$";
	private Pattern pattern;
	private Matcher matcher;

	@Override
	public void initialize(ValidPassword value) {
		log.debug("Poprawnie zainicjalizowano Validator");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		pattern = Pattern.compile(PASS_REGEX_PATTERN);
		matcher = pattern.matcher(value);
		return matcher.matches();
	}
}