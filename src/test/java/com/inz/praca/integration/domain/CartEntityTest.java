package com.inz.praca.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Cart;
import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Product;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class CartEntityTest extends JpaTestBase {
	@Test
	public void shouldPersistCartWhenObjectIsCorrect() {
		Product product = entityManager.persist(new ProductBuilder().withName("nameTest123456789").withPrice(BigDecimal.TEN).createProduct());
		Set<CartItem> cartItems = new HashSet<>();
		cartItems.add(new CartItem(product, 1L));
		Cart cart = entityManager.persistFlushFind(new Cart(cartItems));
		assertThat(cart).isNotNull();
		assertThat(cart.getId()).isNotNull();
		assertThat(cart.getDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
		assertThat(cart.getCartItems()).isNotNull().isNotEmpty();
		assertThat(cart.toString()).contains("Cart(id=");
	}

	@Test
	public void shouldAdd2ProductsAndRemove1ToCart() {
		Set<CartItem> cartItems = new HashSet<>();
		Product product = entityManager.persist(new ProductBuilder().withName("nameTest12345678").withPrice(BigDecimal.TEN).createProduct());
		cartItems.add(new CartItem(product, 1L));
		Product product1 = entityManager.persist(new ProductBuilder().withName("nameTest123456789").withPrice(BigDecimal.ONE).createProduct());
		cartItems.add(new CartItem(product1, 1L));
		Cart cart = entityManager.persistFlushFind(new Cart(cartItems));
		assertThat(cart.getId()).isNotNull();
		assertThat(cart.getCartItems()).isNotNull().isNotEmpty();
		assertThat(cart.getCartItems().size()).isEqualTo(2);
		for (CartItem cartItem : cart.getCartItems()) {
			assertThat(cartItem.getId()).isNotNull();
		}
		assertThat(cart.getDateTime()).isBeforeOrEqualTo(LocalDateTime.now());

		cart.getCartItems().removeIf(v -> v.getProduct().getName().equalsIgnoreCase("nameTest12345678"));

		this.entityManager.persistAndFlush(cart);

		assertThat(cart.getId()).isNotNull();
		assertThat(cart.getCartItems()).isNotNull().isNotEmpty();
		assertThat(cart.getCartItems().size()).isEqualTo(1);
		for (CartItem cartItem : cart.getCartItems()) {
			assertThat(cartItem.getId()).isNotNull();
		}
		assertThat(cart.getDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
	}
}
