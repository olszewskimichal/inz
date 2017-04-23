package com.inz.praca.discount;

import com.inz.praca.orders.Order;
import com.inz.praca.orders.OrderDTO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class NewCustomerDiscountService implements DiscountService {
    @Override
    public void calculateDiscount(Order order, OrderDTO orderDTO) {
        log.info("Znizka nowego klienta 10PLN za zamowienie wieksze niz 50PLN");
        if (order.getPrice().compareTo(BigDecimal.valueOf(50)) >= 0) {
            order.applyDiscountPrice(order.getPrice().subtract(BigDecimal.TEN));
            orderDTO.getCartSession().setTotalPrice(order.getPrice());
        }
    }
}
