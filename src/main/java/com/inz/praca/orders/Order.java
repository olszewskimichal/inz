package com.inz.praca.orders;

import com.inz.praca.cart.Cart;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@ToString
@Table(name = "ORDERS")
public class Order {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CART_ID")
    private final Cart cart;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "DETAIL_ID")
    private final ShippingDetail shippingDetail;
    private BigDecimal price;
    @Id
    @GeneratedValue
    private Long id;

    public Order(Cart cart, ShippingDetail shippingDetail) {
        Assert.notNull(cart, "Zamowienie musi zawierac jakis koszyk");
        Assert.notNull(shippingDetail, "Zamowienie musi zawierac dane dostawy");
        this.price = cart.getCartItems()
                .stream()
                .map(v -> v.getProduct().getPrice().multiply(BigDecimal.valueOf(v.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        this.cart = cart;
        this.shippingDetail = shippingDetail;
    }

    public Order() {
        this.cart = null;
        this.price = BigDecimal.ZERO;
        this.shippingDetail = null;
    }

    public void applyDiscountPrice(BigDecimal discountPrice) {
        this.price = discountPrice;
    }
}
