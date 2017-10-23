package com.inz.praca.units.validators;

import com.inz.praca.UnitTest;
import com.inz.praca.validators.EmailValidator;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class EmailValidatorTest {

    private final EmailValidator emailValidator = new EmailValidator();

    @MockBean
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void testIsValid() {
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