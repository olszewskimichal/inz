package com.inz.praca.orders;

import com.inz.praca.cart.Cart;
import com.inz.praca.cart.CartItem;
import com.inz.praca.cart.CartItemDTO;
import com.inz.praca.cart.CartSession;
import com.inz.praca.discount.DiscountService;
import com.inz.praca.products.ProductNotFoundException;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.registration.CurrentUser;
import com.inz.praca.registration.User;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

@Service
@Slf4j
public class OrderService {

  private final OrderRepository repository;
  private final ProductRepository productRepository;
  private final CartSession cartSession;
  private final WebApplicationContext context;

  @Autowired
  public OrderService(OrderRepository repository, ProductRepository productRepository, CartSession cartSession, WebApplicationContext context) {
    this.repository = repository;
    this.productRepository = productRepository;
    this.cartSession = cartSession;
    this.context = context;
  }

  public Order createOrder(User user, OrderDTO orderDTO) {
    DiscountService discountService = (DiscountService) context.getBean("discountService");
    Order order = new Order(getCartFromSession(orderDTO.getCartSession()), orderDTO.getShippingDetail());
    user.addOrder(order);
    discountService.calculateDiscount(order, orderDTO);
    repository.saveAndFlush(order);
    log.info(order.toString());
    return order;
  }

  private Cart getCartFromSession(CartSession cartSession) {
    Assert.notNull(cartSession, "Nieprawidłowy koszyk");
    Assert.notEmpty(cartSession.getItems(), "Koszyk musi zawierac jakies produkty");
    Set<CartItem> cartItems = new HashSet<>();
    for (CartItemDTO cartItemDTO : cartSession.getItems()) {
      CartItem cartItem = new CartItem(productRepository.findByName(cartItemDTO.getItem().getName()).orElseThrow(() -> new ProductNotFoundException(cartItemDTO.getItem().getName())),
          cartItemDTO.getQuantity().longValue());
      cartItems.add(cartItem);
    }
    return new Cart(cartItems);
  }

  public OrderDTO confirmShippingDetail(ShippingDetail detail) {
    CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CartSession cartSession1 = new CartSession(cartSession);
    OrderDTO orderDTO = new OrderDTO(cartSession1, detail);
    createOrder(user.getUser(), orderDTO);
    cartSession.clearCart();
    return orderDTO;
  }

}
