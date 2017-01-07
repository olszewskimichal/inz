package com.inz.praca.units.service;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.ShippingDetail;
import com.inz.praca.dto.CartSession;
import com.inz.praca.dto.OrderDTO;
import com.inz.praca.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class OrderServiceTest {

	private OrderService orderService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.orderService = new OrderService();
	}

	@Test
	public void shouldCreateOrder() {
		CartSession cartSession = new CartSession();
		cartSession.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		OrderDTO orderDTO = new OrderDTO(cartSession, new ShippingDetail());
		Order order = orderService.createOrder(orderDTO);
		assertThat(order).isNotNull();
	}
}
