package com.inz.praca.units.controller;

import com.inz.praca.UnitTest;
import com.inz.praca.cart.CartController;
import com.inz.praca.cart.CartSession;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.springframework.ui.Model;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@Category(UnitTest.class)
public class CartControllerTest {
    @Mock
    private
    Model model;
    private CartController controller;
    @Mock
    private ProductService productService;

    @Before
    public void setUp() {
        initMocks(this);
        this.controller = new CartController(this.productService, new CartSession());
    }


    @Test
    public void shouldReturnCartPage() {
        assertThat(this.controller.getSessionCart(this.model)).isEqualTo("cart");
    }

    @Test
    public void shouldAddProductToCart() {
        given(this.productService.getProductById(1L)).willReturn(
                new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
        this.controller.addProductToCart(this.model, 1L);
        assertThat(this.controller.getForm().getItems().size()).isEqualTo(1);
        assertThat(this.controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(
                BigDecimal.TEN.stripTrailingZeros());
    }

    @Test
    public void shouldAdd2ProductAndRemove1ProductFromCartAndClearCart() {
        given(this.productService.getProductById(1L)).willReturn(
                new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
        given(this.productService.getProductById(2L)).willReturn(
                new ProductBuilder().withName("nazwa2").withPrice(BigDecimal.ONE).createProduct());
        this.controller.addProductToCart(this.model, 1L);
        this.controller.addProductToCart(this.model, 2L);
        assertThat(this.controller.getForm().getItems().size()).isEqualTo(2);
        assertThat(this.controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(
                BigDecimal.valueOf(11).stripTrailingZeros());

        this.controller.removeProductFromCart(1);
        assertThat(this.controller.getForm().getItems().size()).isEqualTo(1);
        assertThat(this.controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(
                BigDecimal.valueOf(10).stripTrailingZeros());

        this.controller.clearCart(this.model);
        assertThat(this.controller.getForm().getItems().size()).isEqualTo(0);
        assertThat(this.controller.getForm().getTotalPrice().stripTrailingZeros()).isEqualTo(BigDecimal.ZERO);
    }

}
