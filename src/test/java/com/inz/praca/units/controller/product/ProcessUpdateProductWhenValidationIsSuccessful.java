package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.WebTestConstants.ModelAttributeProperty;
import com.inz.praca.WebTestConstants.ModelAttributeProperty.PRODUCT;
import com.inz.praca.WebTestConstants.RedirectView;
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
        this.name = TestStringUtil.createStringWithLength(ProductControllerTestBase.MAX_LENGTH_NAME);
        this.price = BigDecimal.ONE;
        this.category = "others";
    }

    @Test
    public void shouldReturnHttpStatusCodeIsFound() throws Exception {
        this.mockMvc.perform(post("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID)
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(status().isFound());
    }

    @Test
    public void shouldRenderCreateProductView() throws Exception {
        this.mockMvc.perform(post("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID)
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category))
                .andExpect(view().name(RedirectView.PRODUCTS + "/product/1"));
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        this.mockMvc.perform(post("/products/product/edit/{id}", ProductControllerTestBase.PRODUCT_ID)
                .param(PRODUCT.NAME, this.name)
                .param(PRODUCT.PRICE, this.price.toString())
                .param(PRODUCT.CATEGORY, this.category));

        verify(this.productService, times(1)).updateProduct(eq(ProductControllerTestBase.PRODUCT_ID), isA(ProductDTO.class));
    }
}
