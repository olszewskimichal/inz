package com.inz.praca.units.domain;

import com.inz.praca.cart.CartItem;
import com.inz.praca.products.ProductBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CartItemEntityTest {

    @Test
    public void shouldCreateCartItem() {
        try {
            CartItem cartItem = new CartItem(new ProductBuilder().withName("name")
                    .withDescription("desc")
                    .withUrl("url")
                    .withPrice(BigDecimal.TEN)
                    .createProduct(), 0L);
            assertThat(cartItem.getProduct()).isNotNull();
            assertThat(cartItem.getQuantity()).isNotNull().isEqualTo(0L);
            cartItem.setId(1L);
            assertThat(cartItem.getId()).isNotNull().isEqualTo(1L);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldNotCreateCartItemWhenProductIsNull() {
        try {
            new CartItem(null, 5L);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Produkt nie moze być nullem");
        }
    }

    @Test
    public void shouldNotCreateCartItemWhenQuantityIsNull() {
        try {
            new CartItem(new ProductBuilder().withName("name").withPrice(BigDecimal.TEN).createProduct(), null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Ilosc produktów nie może być nullem");
        }
    }

    @Test
    public void shouldNotCreateCartItemWhenQuantityIsNegative() {
        try {
            new CartItem(new ProductBuilder().withName("name").withPrice(BigDecimal.TEN).createProduct(), -1L);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Ilosc produktów musi byc wieksza badz rowna 0");
        }
    }
}
