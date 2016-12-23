package com.inz.praca.units.validators;

import static org.assertj.core.api.Java6Assertions.assertThat;

import javax.validation.ConstraintValidatorContext;

import com.inz.praca.validators.EmailValidator;
import org.junit.Test;

import org.springframework.boot.test.mock.mockito.MockBean;

public class EmailValidatorTest {

	EmailValidator emailValidator = new EmailValidator();

	@MockBean
	ConstraintValidatorContext constraintValidatorContext;

	@Test
	public void testIsValid() throws Exception {
		assertThat(emailValidator.isValid("testowyEmail", constraintValidatorContext)).isFalse();
		assertThat(emailValidator.isValid("testowyEmail@", constraintValidatorContext)).isFalse();
		assertThat(emailValidator.isValid("testowyEmail@o2", constraintValidatorContext)).isFalse();
		assertThat(emailValidator.isValid("testowyEmail@o2.pl", constraintValidatorContext)).isTrue();
	}

	@Test
	public void shouldReturnFalseWhenArgumentIsNull() {
		assertThat(emailValidator.isValid(null, constraintValidatorContext)).isFalse();
	}

}