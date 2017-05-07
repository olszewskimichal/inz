package com.inz.praca.integration.controller;

import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CartControllerTest extends IntegrationTestBase {

    @Autowired
    ProductService productService;

    @Test
    public void shouldShowCartPage() throws Exception {
        mvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }

    @Test
    public void shouldRedirectAfterAddProductToCart() throws Exception {
        Product product = productService.createProductFromDTO(
                new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).withCategory("inne").createProductDTO());
        mvc.perform(get("/cart/add/" + product.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    public void shouldRedirectAfterRemoveProductFromCart() throws Exception {
        mvc.perform(get("/cart/remove/" + 0))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    public void shouldRedirectAfterClearCart() throws Exception {
        mvc.perform(get("/cart/clear"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }

}
