package com.inz.praca.units.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Cart;
import com.inz.praca.dto.CartSession;
import org.junit.Assert;
import org.junit.Test;

public class CartEntityTest {

	@Test
	public void shouldThrownExceptionWhenProductsIsEmpty() {
		try {
			new Cart(new CartSession());
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Koszyk musi zawierac jakies produkty");
		}
	}

	@Test
	public void shouldCreateCartFromCartSession() {

		CartSession cartSession = new CartSession();
		cartSession.addProductDTO(new ProductBuilder().withName("name").withPrice(BigDecimal.TEN).createProductDTO());
		Cart cart = new Cart(cartSession);
		assertThat(cart).isNotNull();
		assertThat(cart.getCartItems()).isNotEmpty();
		assertThat(cart.getCartItems().size()).isEqualTo(1);
	}

	@Test
	public void shouldCreateCart() {
		Cart cart = new Cart(new HashSet<>());
		assertThat(cart).isNotNull();
		assertThat(cart.getCartItems()).isEmpty();
		cart.setId(1L);
		assertThat(cart.getId()).isEqualTo(1L);
		assertThat(cart.toString()).contains("id=1");
	}
}
