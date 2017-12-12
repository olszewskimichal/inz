package com.inz.praca.orders;

import com.inz.praca.cart.CartSession;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class OrderDTO implements Serializable {

  private CartSession cartSession;
  private ShippingDetail shippingDetail;

  public OrderDTO(CartSession cartSession, ShippingDetail shippingDetail) {
    Assert.notNull(shippingDetail, "Szczegóły zamowienia nie moga być nullem");
    Assert.notEmpty(cartSession.getItems(), "Nie mozna stworzyc zamowienia z pusta lista przedmiotow");
    this.cartSession = cartSession;
    this.shippingDetail = shippingDetail;
  }
}
