package com.inz.praca.units.validators;

import com.inz.praca.UnitTest;
import com.inz.praca.validators.PasswordValidator;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class PasswordValidatorTest {

    private final PasswordValidator validator = new PasswordValidator();

    @MockBean
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void shouldReturnFalseWhenPasswordIsIncorrect() {
        assertThat(this.validator.isValid("aaaa", this.constraintValidatorContext)).isFalse();
        assertThat(this.validator.isValid("zaq12wsx", this.constraintValidatorContext)).isFalse();
        assertThat(this.validator.isValid("     zaq1@", this.constraintValidatorContext)).isFalse();
        assertThat(this.validator.isValid(null, this.constraintValidatorContext)).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenPasswordIsCorrect() {
        assertThat(this.validator.isValid("zaq1@WSX", this.constraintValidatorContext)).isTrue();
        this.validator.initialize(null);
    }
}
