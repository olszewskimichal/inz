package com.inz.praca.units.controller.product;

import com.inz.praca.WebTestConstants;
import com.inz.praca.products.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ShowUpdateProductFormWhenProductIsNotFound extends ProductControllerTestBase{

    @Before
    public void throwProductNotFoundException() {
        given(productService.getProductDTOById(PRODUCT_ID)).willThrow(new ProductNotFoundException(PRODUCT_ID));
    }


    @Test
    public void shouldReturnHttpStatusCodeNotFound() throws Exception {
        mockMvc.perform(get("/products/product/edit/{id}", PRODUCT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldRenderNotFoundView() throws Exception {
        mockMvc.perform(get("/products/product/edit/{id}", PRODUCT_ID))
                .andExpect(view().name(WebTestConstants.ErrorView.NOT_FOUND));
    }

}
