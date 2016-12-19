package com.inz.praca.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	public static final String EMAIL_PATTERN = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	private Pattern pattern;
	private Matcher matcher;

	@Override
	public void initialize(ValidEmail value) {
		log.debug("Poprawnie zainicjalizowano Validator");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		if (value == null) {
			return false;
		}
		matcher = pattern.matcher(value);
		return matcher.matches();
	}
}
