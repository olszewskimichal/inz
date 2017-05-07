package com.inz.praca.units.controller;

import com.inz.praca.cart.CartController;
import com.inz.praca.cart.CartSession;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class CartControllerTest {
    @Mock
    Model model;
    private CartController controller;
    @Mock
    private ProductService productService;

    @Before
    public void setUp() {
        initMocks(this);
        controller = new CartController(productService, new CartSession());
    }


    @Test
    public void shouldReturnCartPage() {
        assertThat(controller.getSessionCart(model)).isEqualTo("cart");
    }

    @Test
    public void shouldAddProductToCart() {
        given(productService.getProductById(1L)).willReturn(
                new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
        controller.addProductToCart(model, 1L);
        assertThat(controller.getForm().getItems().size()).isEqualTo(1);
        assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(
                BigDecimal.TEN.stripTrailingZeros());
    }

    @Test
    public void shouldAdd2ProductAndRemove1ProductFromCartAndClearCart() {
        given(productService.getProductById(1L)).willReturn(
                new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
        given(productService.getProductById(2L)).willReturn(
                new ProductBuilder().withName("nazwa2").withPrice(BigDecimal.ONE).createProduct());
        controller.addProductToCart(model, 1L);
        controller.addProductToCart(model, 2L);
        assertThat(controller.getForm().getItems().size()).isEqualTo(2);
        assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(
                BigDecimal.valueOf(11).stripTrailingZeros());

        controller.removeProductFromCart(1);
        assertThat(controller.getForm().getItems().size()).isEqualTo(1);
        assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(
                BigDecimal.valueOf(10).stripTrailingZeros());

        controller.clearCart(model);
        assertThat(controller.getForm().getItems().size()).isEqualTo(0);
        assertThat(controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(BigDecimal.ZERO);
    }

}
