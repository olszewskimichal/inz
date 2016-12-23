package com.inz.praca.units.validators;

import static org.assertj.core.api.Java6Assertions.assertThat;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import com.inz.praca.validators.PriceValidator;
import org.junit.Test;

import org.springframework.boot.test.mock.mockito.MockBean;

public class PriceValidatorTest {
	PriceValidator validator = new PriceValidator();

	@MockBean
	ConstraintValidatorContext constraintValidatorContext;

	@Test
	public void shouldReturnTrue() throws Exception {
		assertThat(validator.isValid(BigDecimal.ZERO, constraintValidatorContext)).isTrue();
		assertThat(validator.isValid(BigDecimal.ONE, constraintValidatorContext)).isTrue();
		assertThat(validator.isValid(BigDecimal.valueOf(1.3), constraintValidatorContext)).isTrue();
		assertThat(validator.isValid(BigDecimal.valueOf(1, 3), constraintValidatorContext)).isTrue();
	}

	@Test
	public void shouldReturnFalseWhenArgumentIsNull() {
		assertThat(validator.isValid(null, constraintValidatorContext)).isFalse();
	}

	@Test
	public void shouldReturnFalseWhenValueIsNegative() {
		assertThat(validator.isValid(BigDecimal.valueOf(-1L), constraintValidatorContext)).isFalse();
	}
}
