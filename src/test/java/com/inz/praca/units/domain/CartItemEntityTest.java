package com.inz.praca.units.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.UnitTest;
import com.inz.praca.cart.CartItem;
import com.inz.praca.products.ProductBuilder;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class CartItemEntityTest {

  @Test
  public void shouldCreateCartItem() {
    try {
      CartItem cartItem = new CartItem(new ProductBuilder().withName("name")
          .withDescription("desc")
          .withUrl("url")
          .withPrice(BigDecimal.TEN)
          .createProduct(), 0L);
      assertThat(cartItem.getProduct()).isNotNull();
      assertThat(cartItem.getQuantity()).isNotNull().isEqualTo(0L);
      cartItem.setId(1L);
      assertThat(cartItem.getId()).isNotNull().isEqualTo(1L);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void shouldNotCreateCartItemWhenProductIsNull() {
    try {
      new CartItem(null, 5L);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Produkt nie moze być nullem");
    }
  }

  @Test
  public void shouldNotCreateCartItemWhenQuantityIsNull() {
    try {
      new CartItem(new ProductBuilder().withName("name").withPrice(BigDecimal.TEN).createProduct(), null);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Ilosc produktów nie może być nullem");
    }
  }

  @Test
  public void shouldNotCreateCartItemWhenQuantityIsNegative() {
    try {
      new CartItem(new ProductBuilder().withName("name").withPrice(BigDecimal.TEN).createProduct(), -1L);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Ilosc produktów musi byc wieksza badz rowna 0");
    }
  }
}
