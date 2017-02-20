package com.inz.praca.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.inz.praca.products.ProductBuilder;
import com.inz.praca.cart.Cart;
import com.inz.praca.cart.CartItem;
import com.inz.praca.orders.Order;
import com.inz.praca.products.Product;
import com.inz.praca.orders.ShippingDetail;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class OrderEntityTest extends JpaTestBase {

	@Test
	public void shouldCreateOrder() {
		Set<CartItem> cartItems = new HashSet<>();
		Product product = entityManager.persist(new ProductBuilder().withName("nameTest222").withPrice(BigDecimal.TEN).createProduct());
		cartItems.add(new CartItem(product, 1L));
		Order order = this.entityManager.persistFlushFind(new Order(new Cart(cartItems), new ShippingDetail("ulica", "miasto", "num", "code")));
		assertThat(order.getId()).isNotNull();
		assertThat(order.toString()).contains("Cart");
		assertThat(order.toString()).contains("ShippingDetail");
	}

	@Test
	public void shouldCreateOrderWithCartAndShippingOrder() {
		ShippingDetail shippingOrder = new ShippingDetail("ul", "ct", "1", "85-891");
		Set<CartItem> cartItems = new HashSet<>();
		Product product = entityManager.persist(new ProductBuilder().withName("nameTest2221").withPrice(BigDecimal.TEN).createProduct());
		cartItems.add(new CartItem(product, 1L));
		Cart cart = new Cart(cartItems);
		Order order = this.entityManager.persistFlushFind(new Order(cart, shippingOrder));
		assertThat(order.getCart()).isNotNull();
		assertThat(order.getCart().getId()).isNotNull();
		assertThat(order.getShippingDetail()).isNotNull();
		assertThat(order.getShippingDetail().getId()).isNotNull();
	}
}
