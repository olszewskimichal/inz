package com.inz.praca.units.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;

import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Product;
import org.junit.Assert;
import org.junit.Test;

public class CartItemEntityTest {

	@Test
	public void shouldCreateCartItem() {
		try {
			CartItem cartItem = new CartItem(new Product("name", "desc", "url", BigDecimal.TEN), 0L);
			assertThat(cartItem.getProduct()).isNotNull();
			assertThat(cartItem.getQuantity()).isNotNull().isEqualTo(0L);
			cartItem.setId(1L);
			assertThat(cartItem.getId()).isNotNull().isEqualTo(1L);
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void shouldNotCreateCartItemWhenProductIsNull() {
		try {
			new CartItem(null, 5L);
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Produkt nie moze być nullem");
		}
	}

	@Test
	public void shouldNotCreateCartItemWhenQuantityIsNull() {
		try {
			new CartItem(new Product("name", "desc", "url", BigDecimal.TEN), null);
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Ilosc produktów nie może być nullem");
		}
	}

	@Test
	public void shouldNotCreateCartItemWhenQuantityIsNegative() {
		try {
			new CartItem(new Product("name", "desc", "url", BigDecimal.TEN), -1L);
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Ilosc produktów musi byc wieksza badz rowna 0");
		}
	}
}
