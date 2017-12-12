package com.inz.praca.units.validators;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.UnitTest;
import com.inz.praca.validators.PriceValidator;
import java.math.BigDecimal;
import javax.validation.ConstraintValidatorContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.boot.test.mock.mockito.MockBean;

@Category(UnitTest.class)
public class PriceValidatorTest {

  private final PriceValidator validator = new PriceValidator();

  @MockBean
  ConstraintValidatorContext constraintValidatorContext;

  @Test
  public void shouldReturnTrue() {
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
