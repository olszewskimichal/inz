package com.inz.praca.integration.repository;

import com.inz.praca.cart.Cart;
import com.inz.praca.cart.CartItem;
import com.inz.praca.integration.JpaTestBase;
import com.inz.praca.orders.Order;
import com.inz.praca.orders.OrderRepository;
import com.inz.praca.orders.ShippingDetail;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class OrderRepositoryTest extends JpaTestBase {

    private static final String NAME = "nazwa12345";
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Fix buga - polegajacego na tym ze zapisujac zamowienie do bazy danych tworzyl sie nowy ten sam produkt mimo iz istnial wczesniej w bazie
     */
    @Test
    public void shouldCreateOrderButNotSaveAnotherProduct() {
        orderRepository.deleteAll();
        Product product = entityManager.persistAndFlush(
                new ProductBuilder().withName(OrderRepositoryTest.NAME).withPrice(BigDecimal.TEN).createProduct());
        int sizeBeforePersistOrder = productRepository.findAll().size();
        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(new CartItem(product, 1L));
        Order order = new Order(new Cart(cartItems), new ShippingDetail("a", "b", "c", "d"));
        orderRepository.save(order);
        assertThat(productRepository.findAll().size()).isEqualTo(sizeBeforePersistOrder);
    }

}
