package com.inz.praca.cart;

import com.inz.praca.products.ProductDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

@ToString
@Getter
@Setter
public class CartItemDTO implements Serializable {

  private ProductDTO item;

  private Integer quantity;

  private BigDecimal price;

  CartItemDTO(ProductDTO item) {
    Assert.notNull(item, "Produkt nie moze być nullem");
    this.item = item;
    quantity = 1;
    price = item.getPrice();
  }

}
