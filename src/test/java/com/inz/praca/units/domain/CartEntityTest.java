package com.inz.praca.units.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;

import com.inz.praca.domain.Cart;
import org.junit.Assert;
import org.junit.Test;

public class CartEntityTest {

	@Test
	public void shouldThrownExceptionWhenProductsIsNull() {
		try {
			new Cart(null);
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Lista elementów koszyka nie może być nullem");
		}
	}

	@Test
	public void shouldCreateCart() {
		try {
			Cart cart = new Cart(new HashSet<>());
			assertThat(cart).isNotNull();
			assertThat(cart.getCartItems()).isEmpty();
			cart.setId(1L);
			assertThat(cart.getId()).isEqualTo(1L);
			assertThat(cart.toString()).contains("id=1");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}
}
