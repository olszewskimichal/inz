package com.inz.praca.units.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.HashSet;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.controller.OrderController;
import com.inz.praca.domain.Cart;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.ShippingDetail;
import com.inz.praca.domain.User;
import com.inz.praca.domain.UserBuilder;
import com.inz.praca.dto.CartSession;
import com.inz.praca.dto.CurrentUser;
import com.inz.praca.dto.OrderDTO;
import com.inz.praca.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class OrderControllerTest {

	private OrderController orderController;

	@Mock
	OrderService orderService;

	@Mock
	Model model;

	@Before
	public void setUp() {
		initMocks(this);
		CartSession cartSession = new CartSession();
		cartSession.addProductDTO(new ProductBuilder().withName("aaaa").withPrice(BigDecimal.TEN).createProductDTO());
		orderController = new OrderController(cartSession, orderService);
	}

	@Test
	public void shouldShowShippingDetailForm() {
		assertThat(orderController.getShippingDetail(model)).isEqualTo("collectCustomerInfo");
		verify(model).addAttribute(new ShippingDetail());
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldConfirmOrder() {
		Authentication auth = new UsernamePasswordAuthenticationToken(new CurrentUser(new UserBuilder().withEmail("aaaaa@o2.pl").withPasswordHash("aaa").build()), null);
		SecurityContextHolder.getContext().setAuthentication(auth);
		given(orderService.createOrder(Matchers.any(User.class), Matchers.any(OrderDTO.class))).willReturn(new Order(new Cart(new HashSet<>()), new ShippingDetail("a", "b", "c", "d")));
		assertThat(orderController.postShippingDetail(new ShippingDetail(), model)).isEqualTo("orderConfirmation");
	}

	@Test
	public void shouldShowOrderLists() {
		Authentication auth = new UsernamePasswordAuthenticationToken(new CurrentUser(new UserBuilder().withEmail("aaaaa@o2.pl").withPasswordHash("aaa").build()), null);
		SecurityContextHolder.getContext().setAuthentication(auth);

		assertThat(orderController.getOrderList(model)).isEqualTo("orderList");
		verify(model).addAttribute("orders", new HashSet<>());
		verifyNoMoreInteractions(model);
	}

}
