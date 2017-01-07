package com.inz.praca.units.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import com.inz.praca.controller.OrderController;
import com.inz.praca.domain.ShippingDetail;
import com.inz.praca.dto.CartSession;
import com.inz.praca.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

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
		orderController = new OrderController(new CartSession(), orderService);
	}

	@Test
	public void shouldShowShippingDetailForm() {
		assertThat(orderController.getShippingDetail(model)).isEqualTo("collectCustomerInfo");
		verify(model).addAttribute(new ShippingDetail());
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldConfirmOrder() {
		assertThat(orderController.postShippingDetail(new ShippingDetail(), model)).isEqualTo("orderConfirmation");
	}

}
