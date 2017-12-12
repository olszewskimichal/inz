package com.inz.praca.discount;

import com.inz.praca.orders.Order;
import com.inz.praca.orders.OrderDTO;
import org.springframework.context.annotation.Configuration;

@Configuration
@FunctionalInterface
public interface DiscountService {

  void calculateDiscount(Order order, OrderDTO orderDTO);
}
