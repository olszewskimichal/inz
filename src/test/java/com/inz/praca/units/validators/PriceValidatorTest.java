package com.inz.praca.units.validators;

import com.inz.praca.UnitTest;
import com.inz.praca.validators.PriceValidator;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class PriceValidatorTest {
    private final PriceValidator validator = new PriceValidator();

    @MockBean
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void shouldReturnTrue() {
        assertThat(this.validator.isValid(BigDecimal.ZERO, this.constraintValidatorContext)).isTrue();
        assertThat(this.validator.isValid(BigDecimal.ONE, this.constraintValidatorContext)).isTrue();
        assertThat(this.validator.isValid(BigDecimal.valueOf(1.3), this.constraintValidatorContext)).isTrue();
        assertThat(this.validator.isValid(BigDecimal.valueOf(1, 3), this.constraintValidatorContext)).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenArgumentIsNull() {
        assertThat(this.validator.isValid(null, this.constraintValidatorContext)).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenValueIsNegative() {
        assertThat(this.validator.isValid(BigDecimal.valueOf(-1L), this.constraintValidatorContext)).isFalse();
    }
}
