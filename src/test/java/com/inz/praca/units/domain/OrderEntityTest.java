package com.inz.praca.units.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.inz.praca.domain.Cart;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.Product;
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
			new Order(new Cart(), null);
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Zamowienie musi zawierac dane dostawy");
		}
	}

	@Test
	public void shouldCreateWhenObjectIsOk() {
		try {
			Order order = new Order(new Cart(new ArrayList<>(), LocalDateTime.now()), new ShippingDetail());
			assertThat(order.getPrice()).isEqualTo(BigDecimal.ZERO);
		}
		catch (IllegalArgumentException e) {
			Assert.fail();
		}
	}

	@Test
	public void shouldCalcPriceWhenCreateOrder() {
		try {
			List<Product> products = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				products.add(new Product("nazwa", "desc", "url", BigDecimal.valueOf(i + 1)));
			}
			Cart cart = new Cart(products, LocalDateTime.now());
			Order order = new Order(cart, new ShippingDetail());
			assertThat(order.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.TEN.stripTrailingZeros());
		}
		catch (IllegalArgumentException e) {
			Assert.fail();
		}
	}

}
