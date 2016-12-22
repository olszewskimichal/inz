package com.inz.praca.integration.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.HashSet;

import com.inz.praca.domain.Cart;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.ShippingDetail;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class OrderEntityTest extends JpaTestBase {

	@Test
	public void shouldCreateOrder() {
		Order order = this.entityManager.persistFlushFind(new Order());
		assertThat(order.getId()).isNotNull();
		assertThat(order.toString()).contains("Order(id");
	}

	@Test
	public void shouldCreateOrderWithCartAndShippingOrder() {
		ShippingDetail shippingOrder = new ShippingDetail("ul", "ct", "1", "85-891");
		Cart cart = new Cart(new HashSet<>());
		Order order = this.entityManager.persistFlushFind(new Order(cart, shippingOrder));
		assertThat(order.getCart()).isNotNull();
		assertThat(order.getCart().getId()).isNotNull();
		assertThat(order.getShippingDetail()).isNotNull();
		assertThat(order.getShippingDetail().getId()).isNotNull();
	}
}
