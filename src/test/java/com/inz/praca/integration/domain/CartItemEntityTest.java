package com.inz.praca.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import com.inz.praca.products.ProductBuilder;
import com.inz.praca.cart.CartItem;
import com.inz.praca.products.Product;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class CartItemEntityTest extends JpaTestBase {

	@Test
	public void shouldPersistCartItemWhenObjectIsCorrect() {
		Product product = entityManager.persist(new ProductBuilder().withName("aaaTest123").withPrice(BigDecimal.TEN).createProduct());
		CartItem cartItem = entityManager.persistFlushFind(new CartItem(product, 4L));
		assertThat(cartItem.getId()).isNotNull();
		assertThat(cartItem.getQuantity()).isEqualTo(4L);
		assertThat(cartItem.getProduct()).isNotNull();
		assertThat(cartItem.getProduct().getId()).isNotNull();
		assertThat(cartItem.getProduct().getName()).isEqualTo("aaaTest123");
		assertThat(cartItem.toString()).contains("Product");
	}
}
