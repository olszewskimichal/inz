package com.inz.praca.units.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.inz.praca.domain.Cart;
import org.junit.Assert;
import org.junit.Test;

public class CartEntityTest {

	@Test
	public void shouldThrownExceptionWhenProductsIsNull() {
		try {
			new Cart(null, LocalDateTime.MAX);
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Lista produktów nie może być nullem");
		}
	}

	@Test
	public void shouldCreateCart() {
		try {
			Cart cart = new Cart(new ArrayList<>(), LocalDateTime.now());
			assertThat(cart).isNotNull();
			assertThat(cart.getProducts()).isEmpty();
			assertThat(cart.getDateTime()).isLessThanOrEqualTo(LocalDateTime.now());
			cart.setId(1L);
			assertThat(cart.getId()).isEqualTo(1L);
			assertThat(cart.toString()).contains("id=1");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}
}
