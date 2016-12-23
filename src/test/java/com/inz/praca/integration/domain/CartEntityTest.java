package com.inz.praca.integration.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import com.inz.praca.domain.Cart;
import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Product;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class CartEntityTest extends JpaTestBase {
	@Test
	public void shouldPersistCartWhenObjectIsCorrect() {
		Cart cart = entityManager.persistFlushFind(new Cart(new HashSet<>()));
		assertThat(cart).isNotNull();
		assertThat(cart.getId()).isNotNull();
		assertThat(cart.getDateTime()).isLessThanOrEqualTo(LocalDateTime.now());
		assertThat(cart.getCartItems()).isNotNull().isEmpty();
		assertThat(cart.toString()).contains("Cart(id=");
	}

	@Test
	public void shouldAdd2ProductsAndRemove1ToCart() {
		Cart cart = entityManager.persistFlushFind(new Cart(new HashSet<>()));
		assertThat(cart.getId()).isNotNull();
		cart.getCartItems().add(new CartItem(new Product("name", "desc", "url", BigDecimal.TEN), 1L));
		cart.getCartItems().add(new CartItem(new Product("name1", "desc2", "url", BigDecimal.ONE), 1L));

		this.entityManager.persistAndFlush(cart);

		assertThat(cart.getId()).isNotNull();
		assertThat(cart.getCartItems()).isNotNull().isNotEmpty();
		assertThat(cart.getCartItems().size()).isEqualTo(2);
		for (CartItem cartItem : cart.getCartItems()) {
			assertThat(cartItem.getId()).isNotNull();
		}
		assertThat(cart.getDateTime()).isLessThanOrEqualTo(LocalDateTime.now());

		cart.getCartItems().removeIf(v -> v.getProduct().getName().equalsIgnoreCase("name"));

		this.entityManager.persistAndFlush(cart);

		assertThat(cart.getId()).isNotNull();
		assertThat(cart.getCartItems()).isNotNull().isNotEmpty();
		assertThat(cart.getCartItems().size()).isEqualTo(1);
		for (CartItem cartItem : cart.getCartItems()) {
			assertThat(cartItem.getId()).isNotNull();
		}
		assertThat(cart.getDateTime()).isLessThanOrEqualTo(LocalDateTime.now());
	}
}
