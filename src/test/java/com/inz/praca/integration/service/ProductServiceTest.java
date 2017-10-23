package com.inz.praca.integration.service;

import com.inz.praca.cart.Cart;
import com.inz.praca.cart.CartItem;
import com.inz.praca.category.CategoryRepository;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.orders.Order;
import com.inz.praca.orders.OrderRepository;
import com.inz.praca.orders.ShippingDetail;
import com.inz.praca.products.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductServiceTest extends IntegrationTestBase {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository repository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Before
    public void setUp() {
        this.orderRepository.deleteAll();
        this.repository.deleteAll();
    }


    @Test
    public void shouldReturnMax20ProductAscSortByIdWhenSizeArgumentIsEqualTo30() {
        for (int i = 0; i < 30; i++) {
            this.repository.save(new ProductBuilder().withName("nazwa" + i).withPrice(BigDecimal.ZERO).createProduct());
        }

        List<Product> products = productService.getProducts(0, 30, "desc", Optional.empty()).getContent();

        assertThat(products.size()).isEqualTo(20); //taki limit ustalony w ProductService
    }

    @Test
    public void shouldUpdateProduct() {
        Product product = this.repository.save(
                new ProductBuilder().withName("nazwaUpdate").withPrice(BigDecimal.ZERO).createProduct());
        ProductDTO productDTO = new ProductDTO(product);
        productDTO.setPrice(BigDecimal.TEN);
        this.productService.updateProduct(product.getId(), productDTO);
        Product updateProduct = this.productService.getProductById(product.getId());
        assertThat(updateProduct.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.TEN.stripTrailingZeros());
    }

    @Test
    public void shouldDeleteProduct() {
        Product product = this.repository.save(
                new ProductBuilder().withName("nazwaUpdate").withPrice(BigDecimal.ZERO).createProduct());
        Integer size = this.repository.findAll().size();
        this.productService.deleteProductById(product.getId());
        assertThat(this.repository.findAll().size()).isEqualTo(size - 1);
    }

    @Test
    public void shouldSetActiveFalseWhenTryDeleteProductWhichIsOrdered() {
        Product product = this.repository.save(
                new ProductBuilder().withName("nazwaUpdate1").withPrice(BigDecimal.ZERO).createProduct());
        this.repository.save(new ProductBuilder().withName("nazwaUpdate2").withPrice(BigDecimal.ZERO).createProduct());
        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(new CartItem(product, 1L));
        this.orderRepository.save(new Order(new Cart(cartItems), new ShippingDetail("a", "b", "c", "d")));
        this.productService.deleteProductById(product.getId());
        Page<Product> products = this.productService.getProducts(1, null, null, Optional.empty());
        assertThat(products.getTotalElements()).isEqualTo(1L);
    }
}
