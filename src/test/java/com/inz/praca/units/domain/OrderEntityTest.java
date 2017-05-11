package com.inz.praca.units.domain;

import com.inz.praca.UnitTest;
import com.inz.praca.cart.Cart;
import com.inz.praca.cart.CartItem;
import com.inz.praca.orders.Order;
import com.inz.praca.orders.ShippingDetail;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class OrderEntityTest {

    @Test
    public void shouldNotCreateWhenCartIsNull() {
        try {
            new Order(null, new ShippingDetail());
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Zamowienie musi zawierac jakis koszyk");
        }
    }

    @Test
    public void shouldNotCreateWhenShippingDetailIsNull() {
        try {
            Set<CartItem> cartItems = new HashSet<>();
            Product product = new ProductBuilder().withName("nameTest222").withPrice(BigDecimal.TEN).createProduct();
            cartItems.add(new CartItem(product, 1L));
            new Order(new Cart(cartItems), null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Zamowienie musi zawierac dane dostawy");
        }
    }

    @Test
    public void shouldCreateWhenObjectIsOk() {
        try {
            Set<CartItem> cartItems = new HashSet<>();
            Product product = new ProductBuilder().withName("nameTest222").withPrice(BigDecimal.TEN).createProduct();
            cartItems.add(new CartItem(product, 1L));
            Order order = new Order(new Cart(cartItems), new ShippingDetail());
            assertThat(order.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.TEN.stripTrailingZeros());
        } catch (IllegalArgumentException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldCalcPriceWhenCreateOrder() {
        try {
            Set<CartItem> cartItems = new HashSet<>();
            for (int i = 0; i < 4; i++) {
                cartItems.add(new CartItem(
                        new ProductBuilder().withName("nazwa").withPrice(BigDecimal.valueOf(i + 1)).createProduct(),
                        1L));
            }
            Cart cart = new Cart(cartItems);
            Order order = new Order(cart, new ShippingDetail());
            assertThat(order.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.TEN.stripTrailingZeros());
        } catch (IllegalArgumentException e) {
            Assert.fail();
        }
    }

}
