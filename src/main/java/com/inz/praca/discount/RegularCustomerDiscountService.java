package com.inz.praca.discount;

import com.inz.praca.orders.Order;
import com.inz.praca.orders.OrderDTO;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegularCustomerDiscountService implements DiscountService {

  @Override
  public void calculateDiscount(Order order, OrderDTO orderDTO) {
    log.info("Znizka stalego klienta 10%");
    order.applyDiscountPrice(order.getPrice().multiply(BigDecimal.valueOf(0.9)));
    orderDTO.getCartSession().setTotalPrice(order.getPrice());
  }
}
