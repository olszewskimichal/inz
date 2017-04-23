package com.inz.praca.orders;

import com.inz.praca.cart.CartSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class OrderDTO implements Serializable {
    private CartSession cartSession;
    private ShippingDetail shippingDetail;

    public OrderDTO(CartSession cartSession, ShippingDetail shippingDetail) {
        Assert.notNull(shippingDetail);
        Assert.notEmpty(cartSession.getItems());
        this.cartSession = cartSession;
        this.shippingDetail = shippingDetail;
    }
}
