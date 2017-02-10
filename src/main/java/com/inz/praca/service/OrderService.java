package com.inz.praca.service;

import java.util.HashSet;
import java.util.Set;

import com.inz.praca.domain.Cart;
import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.User;
import com.inz.praca.dto.CartItemDTO;
import com.inz.praca.dto.CartSession;
import com.inz.praca.dto.OrderDTO;
import com.inz.praca.repository.OrderRepository;
import com.inz.praca.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Slf4j
public class OrderService {

	private final OrderRepository repository;

	private final ProductRepository productRepository;

	@Autowired
	public OrderService(OrderRepository repository, ProductRepository productRepository) {
		this.repository = repository;
		this.productRepository = productRepository;
	}

	public Order createOrder(User user, OrderDTO orderDTO) {
		Order order = new Order(getCartFromSession(orderDTO.getCartSession()), orderDTO.getShippingDetail());
		user.getOrders().add(order);
		repository.saveAndFlush(order);
		log.info(order.toString());
		return order;
	}

	public Cart getCartFromSession(CartSession cartSession) {
		Assert.notNull(cartSession, "Nieprawidłowy koszyk");
		Assert.notEmpty(cartSession.getItems(), "Koszyk musi zawierac jakies produkty");
		Set<CartItem> cartItems = new HashSet<>();
		for (CartItemDTO cartItemDTO : cartSession.getItems()) {
			CartItem cartItem = new CartItem(productRepository.findByName(cartItemDTO.getItem().getName()).get(), cartItemDTO.getQuantity().longValue());
			cartItems.add(cartItem);
		}
		return new Cart(cartItems);
	}

}
