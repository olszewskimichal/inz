package com.inz.praca.cart;

import com.inz.praca.products.ProductDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class CartItemDTO implements Serializable {
    private ProductDTO item;

    private Integer quantity;

    private BigDecimal price = BigDecimal.ZERO;

    public CartItemDTO(ProductDTO item) {
        Assert.notNull(item);
        this.item = item;
        this.quantity = 1;
        this.price = item.getPrice();
    }

}
