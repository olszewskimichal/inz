package com.inz.praca.units.service;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.builders.UserBuilder;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.ShippingDetail;
import com.inz.praca.dto.CartSession;
import com.inz.praca.dto.OrderDTO;
import com.inz.praca.repository.OrderRepository;
import com.inz.praca.repository.ProductRepository;
import com.inz.praca.service.OrderService;
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
		this.orderService = new OrderService(orderRepository, productRepository);
	}

	@Test
	public void shouldCreateOrder() {
		given(productRepository.findByName("aaa")).willReturn(java.util.Optional.ofNullable(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProduct()));
		CartSession cartSession = new CartSession();
		cartSession.addProductDTO(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
		OrderDTO orderDTO = new OrderDTO(cartSession, new ShippingDetail());
		Order order = orderService.createOrder(new UserBuilder().withEmail("aaaa@o2.pl").withPasswordHash("zaq1@WSX").build(), orderDTO);
		assertThat(order).isNotNull();
	}
}
