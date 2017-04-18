package com.inz.praca.units.service;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;

import com.inz.praca.products.ProductBuilder;
import com.inz.praca.registration.UserBuilder;
import com.inz.praca.orders.Order;
import com.inz.praca.orders.ShippingDetail;
import com.inz.praca.cart.CartSession;
import com.inz.praca.orders.OrderDTO;
import com.inz.praca.orders.OrderRepository;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.orders.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderServiceTest {

	private OrderService orderService;

	@Mock
	OrderRepository orderRepository;

	@Mock
	ProductRepository productRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.orderService = new OrderService(orderRepository, productRepository, null);
	}

	@Test
	public void shouldCreateOrder() {
		given(productRepository.findByName("aaa")).willReturn(java.util.Optional.ofNullable(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProduct()));
		CartSession cartSession = new CartSession();
		cartSession.addProduct(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		OrderDTO orderDTO = new OrderDTO(cartSession, new ShippingDetail());
		Order order = orderService.createOrder(new UserBuilder().withEmail("aaaa@o2.pl").withPasswordHash("zaq1@WSX").build(), orderDTO);
		assertThat(order).isNotNull();
	}
}
