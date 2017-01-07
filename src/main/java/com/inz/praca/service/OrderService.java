package com.inz.praca.service;

import com.inz.praca.domain.Cart;
import com.inz.praca.domain.Order;
import com.inz.praca.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

	public Order createOrder(OrderDTO orderDTO) {
		Order order = new Order(new Cart(orderDTO.getCartSession()), orderDTO.getShippingDetail());
		log.info(order.toString());
		return order;
	}

}
