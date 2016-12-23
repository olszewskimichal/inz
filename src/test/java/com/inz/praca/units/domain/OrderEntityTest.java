package com.inz.praca.units.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.inz.praca.domain.Cart;
import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Order;
import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.ShippingDetail;
import org.junit.Assert;
import org.junit.Test;

public class OrderEntityTest {

	@Test
	public void shouldNotCreateWhenCartIsNull() {
		try {
			new Order(null, new ShippingDetail());
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Zamowienie musi zawierac jakis koszyk");
		}
	}

	@Test
	public void shouldNotCreateWhenShippingDetailIsNull() {
		try {
			new Order(new Cart(new HashSet<>()), null);
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Zamowienie musi zawierac dane dostawy");
		}
	}

	@Test
	public void shouldCreateWhenObjectIsOk() {
		try {
			Order order = new Order(new Cart(new HashSet<>()), new ShippingDetail());
			assertThat(order.getPrice()).isEqualTo(BigDecimal.ZERO);
		}
		catch (IllegalArgumentException e) {
			Assert.fail();
		}
	}

	@Test
	public void shouldCalcPriceWhenCreateOrder() {
		try {
			Set<CartItem> cartItems = new HashSet<>();
			for (int i = 0; i < 4; i++) {
				cartItems.add(new CartItem(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.valueOf(i + 1)).createProduct(), 1L));
			}
			Cart cart = new Cart(cartItems);
			Order order = new Order(cart, new ShippingDetail());
			assertThat(order.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.TEN.stripTrailingZeros());
		}
		catch (IllegalArgumentException e) {
			Assert.fail();
		}
	}

}