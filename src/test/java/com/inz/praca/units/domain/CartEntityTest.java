package com.inz.praca.units.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.UnitTest;
import com.inz.praca.cart.Cart;
import com.inz.praca.cart.CartItem;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class CartEntityTest {

  @Test
  public void shouldThrownExceptionWhenCartItemsIsEmpty() {
    try {
      new Cart(new HashSet<>());
      Assert.fail();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Koszyk musi zawierac jakies produkty");
    }
  }

  @Test
  public void shouldCreateCart() {
    Set<CartItem> cartItems = new HashSet<>();
    Product product = new ProductBuilder().withName("nameTest222").withPrice(BigDecimal.TEN).createProduct();
    cartItems.add(new CartItem(product, 1L));
    Cart cart = new Cart(cartItems);
    assertThat(cart).isNotNull();
    assertThat(cart.getCartItems()).isNotEmpty();
    cart.setId(1L);
    assertThat(cart.getId()).isEqualTo(1L);
    assertThat(cart.toString()).contains("id=1");
  }
}
