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
        assertThat(this.emailValidator.isValid("testowyEmail", this.constraintValidatorContext)).isFalse();
        assertThat(this.emailValidator.isValid("testowyEmail@", this.constraintValidatorContext)).isFalse();
        assertThat(this.emailValidator.isValid("testowyEmail@o2", this.constraintValidatorContext)).isFalse();
        assertThat(this.emailValidator.isValid("testowyEmail@o2.pl", this.constraintValidatorContext)).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenArgumentIsNull() {
        assertThat(this.emailValidator.isValid(null, this.constraintValidatorContext)).isFalse();
    }

}