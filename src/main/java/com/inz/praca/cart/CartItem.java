package com.inz.praca.cart;

import com.inz.praca.products.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.*;

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
        this.quantity = null;
        product = null;
    }
}
