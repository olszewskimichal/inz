package com.inz.praca.units.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.UnitTest;
import com.inz.praca.category.Category;
import org.junit.Assert;
import org.junit.Test;

@org.junit.experimental.categories.Category(UnitTest.class)
public class CategoryEntityTest {

  @Test
  public void shouldFailedWhenNameIsNull() {
    try {
      new Category(null, "opis");
      Assert.fail();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Nie moze być pusta nazwa kategorii");
    }
  }

  @Test
  public void shouldFailedWhenNameIsEmpty() {
    try {
      new Category("", "opis");
      Assert.fail();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Nie moze być pusta nazwa kategorii");
    }
  }

  @Test
  public void shouldCreateWhenObjectIsCorrect() {
    try {
      Category category = new Category("aa", "bb");
      assertThat(category.getDescription()).isEqualToIgnoringCase("bb");
      assertThat(category.getName()).isEqualTo("aa");
    } catch (IllegalArgumentException e) {
      Assert.fail();
    }
  }
}
