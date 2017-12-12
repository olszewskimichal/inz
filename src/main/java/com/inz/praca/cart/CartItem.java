package com.inz.praca.cart;

import com.inz.praca.products.Product;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

@Entity
@ToString
@Getter
@Setter
public class CartItem {

  @OneToOne
  @JoinColumn(name = "PRODUCT_ID")
  private final Product product;
  private final Long quantity;
  @Id
  @GeneratedValue
  private Long id;

  public CartItem(Product product, Long quantity) {
    Assert.notNull(quantity, "Ilosc produktów nie może być nullem");
    Assert.notNull(product, "Produkt nie moze być nullem");
    Assert.isTrue(quantity.compareTo(0L) >= 0, "Ilosc produktów musi byc wieksza badz rowna 0");
    this.product = product;
    this.quantity = quantity;
  }

  private CartItem() {
    quantity = null;
    product = null;
  }
}
