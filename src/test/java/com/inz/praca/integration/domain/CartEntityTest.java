package com.inz.praca.integration.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import com.inz.praca.domain.Cart;
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
		assertThat(cart.getProducts()).isNotNull().isEmpty();
		assertThat(cart.toString()).contains("Cart(id=");
	}

	@Test
	public void shouldAdd2ProductsAndRemove1ToCart() {
		Cart cart = entityManager.persistFlushFind(new Cart(new HashSet<>()));
		assertThat(cart.getId()).isNotNull();
		cart.getProducts().add(new Product("name", "desc", "url", BigDecimal.TEN));
		cart.getProducts().add(new Product("name1", "desc2", "url", BigDecimal.ONE));
		this.entityManager.persistAndFlush(cart);
		assertThat(cart.getId()).isNotNull();
		assertThat(cart.getProducts()).isNotNull().isNotEmpty();
		assertThat(cart.getProducts().size()).isEqualTo(2);
		for (Product product : cart.getProducts()) {
			assertThat(product.getId()).isNotNull();
		}

		assertThat(cart.getDateTime()).isLessThanOrEqualTo(LocalDateTime.now());

		cart.getProducts().removeIf(v->v.getName().equalsIgnoreCase("name"));
		this.entityManager.persistAndFlush(cart);
		assertThat(cart.getId()).isNotNull();
		assertThat(cart.getProducts()).isNotNull().isNotEmpty();
		assertThat(cart.getProducts().size()).isEqualTo(1);
		for (Product product : cart.getProducts()) {
			assertThat(product.getId()).isNotNull();
		}
		assertThat(cart.getDateTime()).isLessThanOrEqualTo(LocalDateTime.now());
	}
}
