package com.inz.praca.orders;

import com.inz.praca.cart.Cart;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

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
    price = cart.getCartItems()
        .stream()
        .map(v -> v.getProduct().getPrice().multiply(BigDecimal.valueOf(v.getQuantity())))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
    this.cart = cart;
    this.shippingDetail = shippingDetail;
  }

  public Order() {
    cart = null;
    price = BigDecimal.ZERO;
    shippingDetail = null;
  }

  public void applyDiscountPrice(BigDecimal discountPrice) {
    price = discountPrice;
  }
}
