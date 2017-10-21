package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.products.ProductDTO;
import com.inz.praca.units.TestStringUtil;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProcessUpdateProductWhenValidationIsSuccessful extends ProductControllerTestBase {
    @Before
    public void configureTestCases() {
        name = TestStringUtil.createStringWithLength(MAX_LENGTH_NAME);
        price = BigDecimal.ONE;
        category = "others";
    }

    @Test
    public void shouldReturnHttpStatusCodeIsFound() throws Exception {
        mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                .andExpect(status().isFound());
    }

    @Test
    public void shouldRenderCreateProductView() throws Exception {
        mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category))
                .andExpect(view().name(WebTestConstants.RedirectView.PRODUCTS + "/product/1"));
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        mockMvc.perform(post("/products/product/edit/{id}", PRODUCT_ID)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.NAME, name)
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.PRICE, price.toString())
                .param(WebTestConstants.ModelAttributeProperty.PRODUCT.CATEGORY, category));

        verify(productService, times(1)).updateProduct(eq(PRODUCT_ID), isA(ProductDTO.class));
    }
}
