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

    PasswordValidator validator = new PasswordValidator();

    @MockBean
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void shouldReturnFalseWhenPasswordIsIncorrect() throws Exception {
        assertThat(validator.isValid("aaaa", constraintValidatorContext)).isFalse();
        assertThat(validator.isValid("zaq12wsx", constraintValidatorContext)).isFalse();
        assertThat(validator.isValid("     zaq1@", constraintValidatorContext)).isFalse();
        assertThat(validator.isValid(null, constraintValidatorContext)).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenPasswordIsCorrect() {
        assertThat(validator.isValid("zaq1@WSX", constraintValidatorContext)).isTrue();
        validator.initialize(null);
    }
}
