package com.inz.praca.integration.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;

import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Product;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class CartItemEntityTest extends JpaTestBase {

	@Test
	public void shouldPersistCategoryWhenObjectIsCorrect() {
		CartItem cartItem = entityManager.persistFlushFind(new CartItem(new Product("aaa", "bbb", "ccc", BigDecimal.TEN), 4L));
		assertThat(cartItem.getId()).isNotNull();
		assertThat(cartItem.getQuantity()).isEqualTo(4L);
		assertThat(cartItem.getProduct()).isNotNull();
		assertThat(cartItem.getProduct().getId()).isNotNull();
		assertThat(cartItem.getProduct().getName()).isEqualTo("aaa");
		assertThat(cartItem.toString()).contains("CartItem(id=");
	}
}
