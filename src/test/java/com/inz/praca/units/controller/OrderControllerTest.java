package com.inz.praca.units.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.builders.UserBuilder;
import com.inz.praca.controller.OrderController;
import com.inz.praca.domain.Cart;
import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.Product;
import com.inz.praca.domain.ShippingDetail;
import com.inz.praca.domain.User;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		assertThat(orderController.getShippingDetail(model, redirectAttributes)).isEqualTo("collectCustomerInfo");
		verify(model).addAttribute(new ShippingDetail());
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldRedirectToCartWhenCartIsEmpty() {
		CartSession cartSession = new CartSession();
		orderController = new OrderController(cartSession, orderService);

		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		assertThat(orderController.getShippingDetail(model, redirectAttributes)).isEqualTo("redirect:/cart");
		verify(redirectAttributes).addFlashAttribute("emptyCart", true);
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldConfirmOrder() {
		Authentication auth = new UsernamePasswordAuthenticationToken(new CurrentUser(new UserBuilder().withEmail("aaaaa@o2.pl").withPasswordHash("zaq1@WSX").build()), null);
		SecurityContextHolder.getContext().setAuthentication(auth);
		Set<CartItem> cartItems = new HashSet<>();
		Product product = new ProductBuilder().withName("nameTest222").withPrice(BigDecimal.TEN).createProduct();
		cartItems.add(new CartItem(product, 1L));
		given(orderService.createOrder(Matchers.any(User.class), Matchers.any(OrderDTO.class))).willReturn(new Order(new Cart(cartItems), new ShippingDetail("a", "b", "c", "d")));
		assertThat(orderController.postShippingDetail(new ShippingDetail(), model)).isEqualTo("orderConfirmation");
	}

	@Test
	public void shouldShowOrderLists() {
		Authentication auth = new UsernamePasswordAuthenticationToken(new CurrentUser(new UserBuilder().withEmail("aaaaa@o2.pl").withPasswordHash("zaq1@WSX").build()), null);
		SecurityContextHolder.getContext().setAuthentication(auth);

		assertThat(orderController.getOrderList(model)).isEqualTo("orderList");
		verify(model).addAttribute("orders", new HashSet<>());
		verifyNoMoreInteractions(model);
	}

}
