package com.inz.praca.discount;

import com.inz.praca.orders.Order;
import com.inz.praca.orders.OrderDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoDiscountService implements DiscountService {

  @Override
  public void calculateDiscount(Order order, OrderDTO orderDTO) {
    log.info("Brak znizki");
  }
}
