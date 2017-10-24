package com.inz.praca.units.service;

import com.inz.praca.UnitTest;
import com.inz.praca.cart.CartSession;
import com.inz.praca.discount.NewCustomerDiscountService;
import com.inz.praca.discount.NoDiscountService;
import com.inz.praca.discount.RegularCustomerDiscountService;
import com.inz.praca.orders.*;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.registration.CurrentUser;
import com.inz.praca.registration.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@Category(UnitTest.class)
public class OrderServiceTest {

    @Mock
    private
    OrderRepository orderRepository;
    @Mock
    private
    ProductRepository productRepository;
    @Mock
    private
    WebApplicationContext applicationContext;
    private OrderService orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepository, productRepository, new CartSession(), applicationContext);
    }

    @Test
    public void shouldCreateOrderWithoutDiscount() {
        given(applicationContext.getBean("discountService")).willReturn(new NoDiscountService());
        given(productRepository.findByName("aaa")).willReturn(java.util.Optional.ofNullable(
                new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProduct()));
        CartSession cartSession = new CartSession();
        cartSession.addProduct(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
        OrderDTO orderDTO = new OrderDTO(cartSession, new ShippingDetail());
        Order order = orderService.createOrder(
                new UserBuilder().withEmail("aaaa@o2.pl").withPasswordHash("zaq1@WSX").build(), orderDTO);
        assertThat(order).isNotNull();
        assertThat(order.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(10).stripTrailingZeros());
    }

    @Test
    public void shouldCreateOrderWithRegularCustomerDiscount() {
        given(applicationContext.getBean("discountService")).willReturn(new RegularCustomerDiscountService());
        given(productRepository.findByName("aaa")).willReturn(java.util.Optional.ofNullable(
                new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProduct()));
        CartSession cartSession = new CartSession();
        cartSession.addProduct(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).createProductDTO());
        OrderDTO orderDTO = new OrderDTO(cartSession, new ShippingDetail());
        Order order = orderService.createOrder(
                new UserBuilder().withEmail("aaaa@o2.pl").withPasswordHash("zaq1@WSX").build(), orderDTO);
        assertThat(order).isNotNull();
        assertThat(order.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(9).stripTrailingZeros());
    }

    @Test
    public void shouldCreateOrderWithNewCustomerDiscount() {
        //price bigger then 50
        given(applicationContext.getBean("discountService")).willReturn(new NewCustomerDiscountService());
        given(productRepository.findByName("aaa")).willReturn(java.util.Optional.ofNullable(
                new ProductBuilder().withName("aaa").withPrice(BigDecimal.valueOf(60)).createProduct()));
        CartSession cartSession = new CartSession();
        cartSession.addProduct(
                new ProductBuilder().withName("aaa").withPrice(BigDecimal.valueOf(60)).createProductDTO());
        OrderDTO orderDTO = new OrderDTO(cartSession, new ShippingDetail());
        Order order = orderService.createOrder(
                new UserBuilder().withEmail("aaaa@o2.pl").withPasswordHash("zaq1@WSX").build(), orderDTO);
        assertThat(order).isNotNull();
        assertThat(order.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(50).stripTrailingZeros());

        //price smaller then 50
        given(productRepository.findByName("aaa")).willReturn(java.util.Optional.ofNullable(
                new ProductBuilder().withName("aaa").withPrice(BigDecimal.valueOf(49)).createProduct()));
        cartSession = new CartSession();
        cartSession.addProduct(
                new ProductBuilder().withName("aaa").withPrice(BigDecimal.valueOf(49)).createProductDTO());
        orderDTO = new OrderDTO(cartSession, new ShippingDetail());
        order = orderService.createOrder(
                new UserBuilder().withEmail("aaaa@o2.pl").withPasswordHash("zaq1@WSX").build(), orderDTO);
        assertThat(order).isNotNull();
        assertThat(order.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(49).stripTrailingZeros());
    }

    @Test
    public void shouldThrowExceptionWithEmptyCart(){
        Authentication auth = new UsernamePasswordAuthenticationToken(
                new CurrentUser(new UserBuilder().withEmail("aaaaa@o2.pl").withPasswordHash("zaq1@WSX").build()), null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        ShippingDetail shippingDetail=new ShippingDetail("street","city","num","code");
        assertThatThrownBy(()->orderService.confirmShippingDetail(shippingDetail)).hasMessage("Nie mozna stworzyc zamowienia z pusta lista przedmiotow");
    }

}
