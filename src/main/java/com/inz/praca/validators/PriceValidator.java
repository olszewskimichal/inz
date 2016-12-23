package com.inz.praca.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceValidator implements ConstraintValidator<ValidPrice, BigDecimal> {

	@Override
	public void initialize(ValidPrice value) {
		log.debug("Poprawnie zainicjalizowano Validator ceny");
	}

	@Override
	public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		return (value.compareTo(BigDecimal.ZERO) >= 0) ;


	}
}
