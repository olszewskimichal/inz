package com.inz.praca.units.controller;

import com.inz.praca.UnitTest;
import com.inz.praca.cart.Cart;
import com.inz.praca.cart.CartItem;
import com.inz.praca.cart.CartSession;
import com.inz.praca.orders.*;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.registration.CurrentUser;
import com.inz.praca.registration.User;
import com.inz.praca.registration.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@Category(UnitTest.class)
public class OrderControllerTest {

    @Mock
    private
    OrderService orderService;
    @Mock
    private
    Model model;
    private OrderController orderController;

    @Before
    public void setUp() {
        initMocks(this);
        CartSession cartSession = new CartSession();
        cartSession.addProduct(new ProductBuilder().withName("aaaa").withPrice(BigDecimal.TEN).createProductDTO());
        this.orderController = new OrderController(cartSession, this.orderService);
    }

    @Test
    public void shouldShowShippingDetailForm() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        assertThat(this.orderController.getShippingDetail(this.model, redirectAttributes)).isEqualTo("collectCustomerInfo");
        verify(this.model).addAttribute(new ShippingDetail());
        verifyNoMoreInteractions(this.model);
    }

    @Test
    public void shouldRedirectToCartWhenCartIsEmpty() {
        CartSession cartSession = new CartSession();
        this.orderController = new OrderController(cartSession, this.orderService);

        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        assertThat(this.orderController.getShippingDetail(this.model, redirectAttributes)).isEqualTo("redirect:/cart");
        verify(redirectAttributes).addFlashAttribute("emptyCart", true);
        verifyNoMoreInteractions(this.model);
    }

    @Test
    public void shouldConfirmOrder() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                new CurrentUser(new UserBuilder().withEmail("aaaaa@o2.pl").withPasswordHash("zaq1@WSX").build()), null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        Set<CartItem> cartItems = new HashSet<>();
        Product product = new ProductBuilder().withName("nameTest222").withPrice(BigDecimal.TEN).createProduct();
        cartItems.add(new CartItem(product, 1L));
        given(this.orderService.createOrder(Matchers.any(User.class), Matchers.any(OrderDTO.class))).willReturn(
                new Order(new Cart(cartItems), new ShippingDetail("a", "b", "c", "d")));
        assertThat(this.orderController.postShippingDetail(new ShippingDetail(), this.model)).isEqualTo("orderConfirmation");
    }

    @Test
    public void shouldShowOrderLists() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                new CurrentUser(new UserBuilder().withEmail("aaaaa@o2.pl").withPasswordHash("zaq1@WSX").build()), null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThat(this.orderController.getOrderList(this.model)).isEqualTo("orderList");
        verify(this.model).addAttribute("orders", new HashSet<>());
        verifyNoMoreInteractions(this.model);
    }

}
