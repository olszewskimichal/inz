package com.inz.praca.service;

import com.inz.praca.domain.Cart;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.User;
import com.inz.praca.dto.OrderDTO;
import com.inz.praca.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order createOrder(User user, OrderDTO orderDTO) {
		Order order = new Order(new Cart(orderDTO.getCartSession()), orderDTO.getShippingDetail());
		orderRepository.save(order);
		user.getOrders().add(order);
		log.info(order.toString());
		return order;
	}

}
