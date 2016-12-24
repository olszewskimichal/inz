package com.inz.praca.units.validators;

import static org.assertj.core.api.Java6Assertions.assertThat;

import javax.validation.ConstraintValidatorContext;

import com.inz.praca.validators.PasswordValidator;
import org.junit.Test;

import org.springframework.boot.test.mock.mockito.MockBean;

public class PasswordValidatorTest {

	PasswordValidator validator = new PasswordValidator();

	@MockBean
	ConstraintValidatorContext constraintValidatorContext;

	@Test
	public void shouldReturnFalseWhenPasswordIsIncorrect() throws Exception {
		assertThat(validator.isValid("aaaa", constraintValidatorContext)).isFalse();
		assertThat(validator.isValid("zaq12wsx", constraintValidatorContext)).isFalse();
		assertThat(validator.isValid("     zaq1@", constraintValidatorContext)).isFalse();
	}

	@Test
	public void shouldReturnTrueWhenPasswordIsCorrect() {
		assertThat(validator.isValid("zaq1@WSX", constraintValidatorContext)).isTrue();
	}
}
